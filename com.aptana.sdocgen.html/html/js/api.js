/**
 * This file Copyright (c) 2005-2009 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Aptana provides a special exception to allow redistribution of this file
 * with certain Eclipse Public Licensed code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 *
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 *
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 *
 * Any modifications to this file must keep this entire header intact.
 */

// attach the items
$(document).ready(function() {
        $('th.browser').each(function() {
                var $entry = $(this);
                var browser = jQuery.trim($entry.text());
                $entry.append("<br /><img src='button_off.gif' browser=\"" + browser + "\" onclick='toggleRows(\"" + browser + "\", this)' style='padding-top:5px' title='Hide items not available on this platform'>");
        });

		$('.details-property').hide();
		$('.details-method').hide();
		if (window.location.toString().indexOf("visibility=advanced") > 0) {
			$('.advanced').attr("hide", "false");
		}
		else
		{
			$('.advanced').hide().attr("hide", "true");			
		}
		$('.item').attr("details", "false");
});

var arr = new Array();

function toggleAdvanced(image)
{
    	var val = image.state;
        if(val == undefined)
        {
        	val = false;
        }

        if (val == false)
		{
         	var tr = $('.advanced').fadeIn("fast");
        	tr.attr("hide", "false");
			tr.each(function(index, obj) {
					var details = $(this).attr("details");
    				if (details == "true") {
						$(this).next().show();
					}
				});
			$(".advanced-toggle").attr("src", "hide_advanced.gif");
        }
        else
        {
        	var tr = $('.advanced').fadeOut("fast");
        	tr.attr("hide", "true");
			tr.next().hide();
            $(".advanced-toggle").attr("src", "show_advanced.gif");
        }
        image.state = !val;
}

function toggleRows(browserName, image)
{
        var val = arr[browserName];
        if(val == undefined)
        {
               val = false;
        }

        if (val == true) {

                var tr = $('td.no[title="' + browserName + '"]').parent();
				tr.each(function(index, obj) {
					var hide = $(this).attr("hide");
    				if (hide == "false" || hide == undefined) {
						$(this).fadeIn("fast");
					}
					
					var details = $(this).attr("details");
    				if (details == "true") {
						$(this).next().show();
					}
				});

                $('img[browser="' + browserName + '"]').attr("src", "button_off.gif");

                for (var word in arr)
                {
                        var state = arr[word];
                        if(word != browserName && state == true)
                        {
                                var tr2 = $('td.no[title="' + word + '"]').parent();
                                tr2.hide();
                                tr2.next().hide();
                        }
                }
        }
        else
        {
                var tr = $('td.no[title="' + browserName + '"]').parent();
                tr.fadeOut("fast");
                tr.next().hide();
                $('img[browser="' + browserName + '"]').attr("src", "button_on.gif");
        }
        arr[browserName] = !val;
}

function toggleRowDetails(row)
{
	var state = $(row).parent().parent().attr("details");
	if(state == undefined)
	{
		state = "true";
	}
	
	$(row).empty();
	if(state == "false")
	{
		$(row).append("Hide Details");
	}
	else
	{
		$(row).append("Show Details");		
	}
	
	$(row).parent().parent().next().toggle();
	$(row).parent().parent().attr("details", state == "false" ? "true" : "false");
}

function toggleClickDetails(row)
{
	$(row).parent().parent().parent().next().toggle();
	var state = $(row).parent().parent().parent().attr("details");
	$(row).parent().parent().parent().attr("details", state == "false" ? "true" : "false");
}