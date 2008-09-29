NAME=thesis
NAMECONV=latin-thesis
BIBFILE=zdroje.bib
BIBFILECONV=latin-zdroje.bib
LATEX=cslatex
PDFLATEX=pdfcslatex

all: dvi pdf ps
clean:
	@echo "Zahajuji uklid"
	-rm *.aux *.dvi *.log *.ps *.idx *.ind *.bbl *.out *.ilg *.blg latin-*
	@echo "Uklid hotov"
convert: $(NAME).tex $(BIBFILE)
	iconv -f UTF8 -t latin2 < $(NAME).tex > $(NAMECONV).tex
	iconv -f UTF8 -t latin2 < $(BIBFILE) > $(BIBFILECONV)
$(NAMECONV).tex: convert
$(BIBFILECONV): convert 
vlna: $(NAMECONV).tex
	/packages/run/vlna/bin/vlna -r -m -l $(NAMECONV).tex
dvi: $(NAMECONV).dvi
pdf: $(NAMECONV).pdf
ps: $(NAMECONV).ps
$(NAMECONV).dvi: $(NAMECONV).tex $(NAMECONV).bbl
	@echo "Generuji DVI"
	$(PDFLATEX) -output-format=dvi $(NAMECONV).tex
	@while grep -qs 'LaTeX warning:.*references*' $(NAMECONV).log; do echo; $(PDFLATEX) -output-format=dvi $(NAMECONV).tex; done
$(NAMECONV).pdf: $(NAMECONV).tex vlna $(NAMECONV).bbl
	#$(PDFLATEX) -output-format=pdf $(NAMECONV).tex
	while grep -qis 'LaTeX warning:.*references*' $(NAMECONV).log; do echo; $(PDFLATEX) -output-format=pdf $(NAMECONV).tex ; done
$(NAMECONV).ps: $(NAMECONV).dvi $(NAMECONV).tex
	dvips -o $(NAMECONV).ps $(NAMECONV).dvi
	@while grep -iqs 'LaTeX warning:.*references*' $(NAMECONV).log; do echo; dvips -o $(NAMECONV).ps $(NAMECONV).dvi; done
$(NAMECONV).bbl: $(BIBFILECONV) $(NAMECONV).aux
	bibtex $(NAMECONV)

$(NAMECONV).aux: $(NAMECONV).tex
	$(LATEX) $(NAMECONV).tex
