<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xavia="http://endeca.com/schema/xavia/2010">
	<xsl:output method="html" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" />
	<xsl:param name="linkPrefix" />
	<xsl:param name="contextPath" />
	<xsl:template match="/">
		<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
				<title>XSLT</title>
				<style type="text/css">
					div {
					padding: 0px;
					padding-left: 2px;
					}

					div.Item {
					border-color: red;
					}

					div.Property {
					border-color: green;
					}

					div.List {
					border-color: blue;
					border-width: thin;
					border-style: solid;
					padding:
					1px;
					}

					.head {
					background-color: #eeeeee;
					}

					.name {
					font-style: italic;
					}

					.type {
					font-weight: bold;
					}
				</style>
				<script type="text/javascript">
					<xsl:attribute name="src">
					<xsl:value-of select="concat($contextPath, '/js/jquery-1.4.4.min.js')" />
					</xsl:attribute>
				</script>
				<script type="text/javascript">
					jQuery(document).ready(function() {
					$('.accordion .head').click(function() {
					$(this).next().toggle(50);
					return false;
					}).next().hide();
					$('.record').parents().show();
					$('.record').next().show();
					});
				</script>
			</head>
			<body>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>
	<!-- standard copy template -->
	<xsl:template match="@*" mode="disabled">
		<div class="attribute">
			<span class="key">
				<xsl:value-of select="name()" />
			</span>
			=
			<span class="value">
				<xsl:value-of select="." />
			</span>
		</div>
	</xsl:template>
	<xsl:template match="xavia:*">
		<xsl:call-template name="divblock">
			<xsl:with-param name="name" select="@type|@class" />
			<xsl:with-param name="type" select="name()" />
		</xsl:call-template>
	</xsl:template>
	<xsl:template match="xavia:Property">
		<xsl:call-template name="divblock">
			<xsl:with-param name="name" select="@name" />
			<xsl:with-param name="type" select="name()" />
		</xsl:call-template>
	</xsl:template>
	<xsl:template match="xavia:Property[@name='action' or @name='detailsAction']">
		<xsl:call-template name="divblock">
			<xsl:with-param name="type" select="name()" />
			<xsl:with-param name="name">
				<xsl:value-of select="@name" />
			</xsl:with-param>
			<xsl:with-param name="applyTemplates" select="false()" />
		</xsl:call-template>
		<a>
			<xsl:attribute name="href"><xsl:value-of select="$linkPrefix" /><xsl:value-of
				select="." /></xsl:attribute>
			<xsl:value-of select="." />
		</a>
	</xsl:template>

	<xsl:template
		match="xavia:Item[@class='com.endeca.infront.cartridge.model.Record' or @class='com.endeca.infront.tui.anite.handlers.RecordWithDimensionInfo']">
		<div class="record accordion">
			<div class="head">Record</div>
			<div>
				<div>Attributes</div>
				<table class="attributes">
					<xsl:apply-templates mode="recordProperties"
						select="xavia:Property[@name='attributes']/xavia:Item/xavia:Property" />
				</table>
				<xsl:apply-templates select="*[not(@name='attributes')]" />
			</div>
		</div>
	</xsl:template>
	<xsl:template
		match="xavia:Item[@class='com.endeca.infront.cartridge.model.Refinement']">
		<div class="refinement accordion">
			<div class="head">Refinement</div>
			<div>
				<div>Refinement content</div>
				<xsl:apply-templates/>
			</div>
		</div>
	</xsl:template>
	<xsl:template mode="recordProperties" match="xavia:Property">
		<tr>
			<td>
				<xsl:value-of select="@name"></xsl:value-of>
			</td>
			<td>
				<xsl:value-of select="."></xsl:value-of>
			</td>
		</tr>
	</xsl:template>
	<xsl:template match="xavia:Boolean|xavia:Number|xavia:String">
		<xsl:value-of select="." />
	</xsl:template>
	<xsl:template match="xavia:List">
		<div>
			<xsl:attribute name="class"><xsl:value-of select="name()" /></xsl:attribute>
			<xsl:apply-templates select="child::node()" />
		</div>
	</xsl:template>

	<xsl:template name="divblock">
		<xsl:param name="type" />
		<xsl:param name="name" />
		<xsl:param name="applyTemplates" select="true()" />
		<div>
			<xsl:attribute name="class"><xsl:value-of select="name()" /> accordion</xsl:attribute>
			<div class="head">
				<span class="type">
					<xsl:value-of select="$type" />
				</span>
				:
				<span class="name">
					<xsl:value-of select="$name" />
				</span>
			</div>
			<xsl:if test="$applyTemplates">
				<div>
					<xsl:apply-templates select="child::node()" />
				</div>
			</xsl:if>
		</div>
	</xsl:template>
</xsl:stylesheet>