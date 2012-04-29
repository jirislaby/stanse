#!/usr/bin/perl -w
use strict;

my $nomark_dir = "/home/xslaby/src-28/";
my $source_dir = "/l/latest/tmp/linux1/";

sub whitespace($) {
	my $_ = shift;
	s/\s//g;
	return $_;
}

sub find_line($$$) {
	my $fst = shift;
	my @all = @$fst;
	my $pos = shift;
	my $file = shift;
	my $back = 0;
	while ($pos >= 0) {
		my $markline = $all[$pos];
		if ($markline =~ /^\s*#/) {
			die "no armored for $file $markline"
				if ($markline !~ /^\s*#\s*([0-9]+)\s*".*armored\.c"/);
			return $1 + $back - 1;
		}
		$pos--;
		$back++;
	}
	die "WTF?";
}

sub handle_markers($$$$) {
	my $file = shift;
	my $line = shift;
	my $wanted = shift;
	my $orig_ret = shift;

	open(MARK, "$file.preproc") || die "cannot open $file.preproc";
	my @all = <MARK>;
	close MARK;
	my $lcnt = 0;
	my $ret = undef;
	my $start = 1;
	for (my $pos = 0; $pos < scalar @all; $pos++) {
		my $markline = $all[$pos];
		if ($markline =~ /^\s*#/) {

			next;
		}
		# XXX comment this for new gcc's
		next if ($start && $markline =~ /^\s*$/);
		$start = 0;
		$lcnt++;
		if ($lcnt == $line) {
			$markline = whitespace($markline);
			if ($markline ne $wanted) {
				die "ineq at $file ($line): " . $markline .
					" to $wanted";
			}
			$ret = find_line(\@all, $pos, $file);
			open(ORIG, "$source_dir$file") || die "cannot open $file";
			@all = <ORIG>;
			close ORIG;
			my $orig = whitespace($all[$ret-1]);
			@all = undef;
			$$orig_ret = $orig;
			last;
		}
	}

	return $ret;
}

while (<>) {
	chomp;
	my ($file, $line) = split /\|/;
	open(NOMARK, "$nomark_dir/$file") ||
		die "cannot open $nomark_dir/$file";
	my @all = <NOMARK>;
	close NOMARK;
	my $wanted = whitespace($all[$line-1]);
	@all = undef;
	my $orig;
	my $marked = handle_markers($file, $line, $wanted, \$orig);
	$wanted =~ s/__st_//g;
	$wanted =~ s/_st__//g;
	$orig =~ s@/\*[^*]*\*/@@g;
	if ((length $orig > 5 || $wanted eq "}") && $wanted eq $orig) {
		print "OK1 $file $line $file $marked\n";
	} elsif ($wanted eq $orig) {
		print "OK2 $file $line $file $marked\n\t$orig\n";
	} else {
		print "TODO $file $line $file $marked\n\t$wanted == $orig\n";
	}
}
