#!/usr/bin/perl -w
use strict;
use CGI ':standard';
use DBI;

our(%OKfiles);
require 'dbs.pl';

my $cg=new CGI;
$cg->default_dtd('-//W3C//DTD XHTML 1.0 Strict//EN',
                'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd');
print $cg->header(-charset=>"UTF-8", -expires=>"1h");
print $cg->start_html(-dtd=>"yes", -lang=>"cs", -title=>"Stanse results",
	-encoding=>"UTF-8");

my $datafile = $cg->param('db');
if (!defined $datafile || !exists $OKfiles{$datafile}) {
	print "<p>Select database:</p>\n";
	foreach (sort { $OKfiles{$a} cmp $OKfiles{$b} } keys %OKfiles) {
		print qq(<div><a href="?db=$_">$OKfiles{$_}</a></div>\n);
	}
	goto end;
}

my $filter = $cg->param('filter');
if (defined $filter) {
	$filter =~ s/%//g;
	my $flen = length $filter;
	if ($flen < 2) {
		if ($flen > 0) {
			print qq|<p style="color: red;">Filter must be at |,
				qq|least 2 characters.</p>\n|;
		}
		$filter = undef;
	}
} else {
	$filter = undef;
}

my $filter_name = undef;
if (defined $filter) {
	$filter_name = $filter;
	$filter = '%' . $filter . '%';
} else {
	$filter = '%';
}

my $dbh = DBI->connect("dbi:SQLite:dbname=$datafile.db","","") ||
	die "connect to db error: " . DBI::errstr;

my $count = $dbh->prepare("SELECT count(id) FROM errors WHERE file LIKE ?");
$count->execute($filter);
@_ = $count->fetchrow_array;
$count->finish;
$count = $_[0];

print $cg->h2('Errors found by Stanse'), "\n";
print qq|<p>See <a href="http://stanse.fi.muni.cz/">Stanse homepage</a> for |,
	qq|more info.</p>\n|;
print $cg->start_form(-action=>"", -method=>"GET");
print "<div>", $cg->textfield(-name=>'filter', -size=>20),
	$cg->submit('Filter files'), "</div>\n";
print $cg->hidden('db', $datafile), "\n";
print $cg->end_form, "\n";
print qq|<p>$count errors (false positives including) found in <strong>$OKfiles{$datafile}</strong> kernel|;
print qq| for filter '$filter_name'| if (defined $filter_name);
print qq|:</p>\n|;
print qq|<div style="font-size: 75%;"><em>The number before pipe is |,
	qq|importance (the lower the better).</em></div>\n|;

my $errors = $dbh->prepare("SELECT * FROM errors WHERE file LIKE ? ORDER BY checker,importance,error,file,line");
$errors->execute($filter);

my $checker = "";

while ($_ = $errors->fetchrow_hashref) {
	print qq(<div style="margin-top: 1em; font-size: 120%; font-family: Sans-serif;">$$_{checker}</div>\n) unless ($checker eq $$_{checker});
	print '<div>';
	for (my $a = 3 - length $$_{importance}; $a > 0; $a--) {
		print "&nbsp;";
	}
	print qq($$_{importance}| $$_{error} <a href="error.cgi?db=$datafile&id=$$_{id}">$$_{file}</a> line ),
		qq(<a href="error.cgi?db=$datafile&id=$$_{id}#l$$_{line}">$$_{line}</a></div>\n);
	$checker = $$_{checker};
}

$dbh->disconnect;

end:
print $cg->end_html, "\n";

0;
