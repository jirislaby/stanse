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

my $dbh = DBI->connect("dbi:SQLite:dbname=$datafile.db","","") ||
	die "connect to db error: " . DBI::errstr;

my $count = $dbh->prepare("SELECT count(id) FROM errors");
$count->execute;
@_ = $count->fetchrow_array;
$count->finish;
$count = $_[0];

print $cg->h2('Errors found by Stanse');
print '<p>See <a href="http://stanse.fi.muni.cz/">Stanse homepage</a> for ' .
	'more info.</p>';
print qq|<p>$count errors (false positives including) found in <strong>$OKfiles{$datafile}</strong> kernel:</p>|;
print qq|<div style="font-size: 75%;"><em>The number before pipe is importance.</em></div>|;

my $errors = $dbh->prepare("SELECT * FROM errors ORDER BY checker,importance,error,file,line");

$errors->execute();

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
