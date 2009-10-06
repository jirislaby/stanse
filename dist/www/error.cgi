#!/usr/bin/perl -w
use strict;
use CGI ':standard';
use DBI;
use XML::XPath;

our(%OKfiles);
require 'dbs.pl';

sub escape($$$) {
	my ($cg, $xp, $el) = @_;
	return $cg->escapeHTML($xp->findvalue($el));
}

my $cg=new CGI;
$cg->default_dtd('-//W3C//DTD XHTML 1.0 Strict//EN',
                'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd');

my $datafile = $cg->param('db');
if (!defined $datafile || !exists $OKfiles{$datafile}) {
	my $url = $cg->url();
	my $rel = $cg->url(-relative=>1);
	$url =~ s/$rel$//;
	print $cg->redirect($url);
	exit 0;
}

print $cg->header(-charset=>"UTF-8");
print $cg->start_html(-dtd=>"yes", -lang=>"cs", -title=>"Stanse error",
	-encoding=>"UTF-8");

my $id = $cg->param("id");
if (!defined $id) {
	print qq(<p>No ID defined</p>\n);
	goto end;
}

my $dbh = DBI->connect("dbi:SQLite:dbname=$datafile.db","","") ||
	die "connect to db error: " . DBI::errstr;

my $errors = $dbh->prepare("SELECT * FROM errors WHERE id=?");

$errors->execute($id);

my $err = $errors->fetchrow_hashref; # there should be only one

$errors->finish;

if (!$err) {
	print "<p>wrong id</p>";
	goto end;
}

my $xp = XML::XPath->new(xml => $$err{errorXML});

if (!$xp) {
	print "<p>can't parse XML</p>";
	goto end;
}

my %lines;
my %lines_prev;
my %lines_next;
my $sum_fp = 0;

my $db1 = DBI->connect("dbi:SQLite:dbname=reports.db", "","");

if (defined $cg->param('OK') || defined $cg->param('FP')) {
	my $isOK = defined $cg->param('OK');
	if ($db1) {
		my $q = $db1->prepare('INSERT INTO reports(ip, db, errid, ' .
				'type, unique_entry, stamp) VALUES ' .
				q|(?,?,?,?,?,datetime('now'))|);
		if ($q->execute($cg->remote_addr, $datafile, $id,
				$isOK ? -1 : 1, $cg->remote_addr . "-" .
				$datafile . "-" .$id)) {
			print qq(<p style="color: red;">marked as ), $isOK ?
				"OK" : "false positive", "</p>\n";
		} else {
			print qq(<p style="color: red;">Cannot add more than ) .
				"once</p>\n";
		}
		$q->finish;
	}
}
if ($db1) {
	my $q = $db1->prepare('select sum(type) as s from reports where ' .
			'errid=? and db=?') || die "can't prepare sum";
	$q->execute($id, $datafile);
	my $sum = $q->fetchrow_hashref;
	$q->finish;
	$sum_fp = defined $$sum{s} ? $$sum{s} : 0;
}

$db1->disconnect;

print "<h2>", escape($cg, $xp, '/error/short_desc'), "</h2>\n",
      "<div><strong>File:</strong> ", $$err{file}, "</div>\n",
      "<div><strong>Full description:</strong> ",
      escape($cg, $xp, '/error/full_desc'), "</div>\n",
      "<div><strong>Importance:</strong> ",
      escape($cg, $xp, '/error/importance'), "</div>\n",
      "<div><strong>Checker:</strong> ",
      escape($cg, $xp, '/error/checker_name'), "</div>\n",
      "<div><strong>Trace:</strong></div>\n";
my $locs = $xp->findnodes('/error/traces/trace[1]/locations/location');
my $prevline = undef;
foreach my $loc ($locs->get_nodelist) {
	my $line = $loc->findvalue('line');
	my $desc = escape($cg, $loc, 'description');
	$lines{$line} = $desc;
	$lines_prev{$line} = $prevline if defined $prevline;
	$lines_next{$prevline} = $line if defined $prevline;
	print qq(<div style="margin-left: 2em;"><a href="#l$line">),
	      qq(line $line</a>: $desc</div>\n);
	$prevline = $line;
}

print "<div><strong>This one is:</strong></div>\n",
	$cg->start_form(-action=>""),
	"<div>", $cg->submit('OK'), " ", $cg->submit('FP', 'False positive'),
	"</div>\n",
	$cg->hidden('db', $datafile), $cg->hidden('id', $id),
	$cg->end_form(), "\n",
	"<div><strong>False positive index (the lower the better):</strong> " .
	"$sum_fp</div>\n",
	qq|<div style="margin-top: 1em;"><strong>File contents (this file is |,
	"distributed under the terms specified in the original ",
	"file):</strong></div>\n<pre>\n";

my $input = "src-$datafile/$$err{file}";

if (! -f $input && -f $input . ".lzma") {
	$input = "lzcat $input.lzma|";
}

if (!open SRC, $input) {
	print qq(<p style="color: red;">Can't read source file</p>);
	goto end;
}
my $line = 1;
while (<SRC>) {
	printf qq(<a name="l%d">%5d</a>|), $line, $line;
	print;
	if (exists $lines{$line}) {
		print qq(     |<span style="background: yellow;">),
		      qq($lines{$line}</span>);
		print qq( <a href="#l$lines_prev{$line}">prev</a>)
			if (exists $lines_prev{$line});
		print qq( <a href="#l$lines_next{$line}">next</a>)
			if (exists $lines_next{$line});
		print "\n";
	}
	$line++;
}
close SRC;
end:
print qq(</pre>\n);

$dbh->disconnect;

end:
print $cg->end_html, "\n";

0;
