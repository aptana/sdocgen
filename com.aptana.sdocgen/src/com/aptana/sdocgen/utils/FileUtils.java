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
package com.aptana.sdocgen.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * @author Kevin Lindsey
 *
 */
public class FileUtils
{
	/**
	 * getStreamText
	 * 
	 * @param stream
	 * @return String
	 */
	public static String getStreamText(InputStream stream)
	{
		String result = null;
		
		try
		{
			// create output buffer
			StringWriter sw = new StringWriter();

			// read contents into a string buffer
			try
			{
				// get buffered reader
				InputStreamReader isr = new InputStreamReader(stream);
				BufferedReader reader = new BufferedReader(isr);

				// create temporary buffer
				char[] buf = new char[1024];

				// fill buffer
				int numRead = reader.read(buf);

				// keep reading until the end of the stream
				while (numRead != -1)
				{
					// output temp buffer to output buffer
					sw.write(buf, 0, numRead);

					// fill buffer
					numRead = reader.read(buf);
				}
			}
			finally
			{
				if (stream != null)
				{
					stream.close();
				}
			}

			// return string buffer's content
			result = sw.toString();
		}
		catch (Exception e)
		{
		}
		
		return result;
	}
}
