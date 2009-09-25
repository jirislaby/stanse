<?xml version='1.0'?>
<!-- to be run by xsltproc sort.xsl source.xml -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/database">
<database>
	<xsl:copy-of select="files" />
	<xsl:copy-of select="internals" />
	<xsl:copy-of select="checkers" />
	<xsl:copy-of select="checkfails" />

	<errors>
		<xsl:for-each select="errors/error">
			<xsl:sort select="checker_name"/>
			<xsl:sort select="short_desc"/>
			<xsl:sort select="traces/trace[1]/locations/location[1]/unit"/>
			<xsl:copy-of select="." />
		</xsl:for-each>
	</errors>
</database>
</xsl:template>
</xsl:stylesheet>
