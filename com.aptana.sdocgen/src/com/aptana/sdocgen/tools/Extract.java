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
package com.aptana.sdocgen.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aptana.sdocgen.utils.FileUtils;

public class Extract
{
	private static final String DOCUMENTATION_END = "*/";
	private static final String DOCUMENTATION_START = "/**";
	
	/**
	 * main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		if (args.length == 2)
		{
			File fileOrDir = new File(args[0]);
			File outputFile = new File(args[1]);
			
			try
			{
				if (fileOrDir.exists())
				{
					List<String> comments = new ArrayList<String>();
					File[] files;
					
					if (fileOrDir.isDirectory())
					{
						files = fileOrDir.listFiles(new FilenameFilter()
						{
							/**
							 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
							 */
							public boolean accept(File dir, String name)
							{
								return name.toLowerCase().endsWith(".js");
							}
						});
					}
					else
					{
						files = new File[] { fileOrDir };
					}
					
					if (outputFile.exists() == false)
					{
						if (outputFile.createNewFile() == false)
						{
							System.out.println("Unable to create output file: " + args[1]);
						}
					}
					
					if (outputFile.exists())
					{
						for (int i = 0; i < files.length; i++)
						{
							File file = files[i];
							
							comments.addAll(getComments(file));
						}
						
						FileWriter fw = new FileWriter(outputFile);
						BufferedWriter writer = new BufferedWriter(fw);
						
						for (int i = 0; i < comments.size() - 1; i++)
						{
							String comment = comments.get(i);
							
							writer.write(comment);
							writer.newLine();
							writer.newLine();
						}
						
						String comment = comments.get(comments.size() - 1);
						writer.write(comment);
						writer.newLine();
						
						writer.close();
					}
					
					System.out.println("Extraction complete");
				}
				else
				{
					System.out.println("Source file or directory does not exist: " + args[0]);
				}
			}
			catch (Exception e)
			{
				System.out.println("An error occurred: " + e.getMessage());
			}
		}
		else
		{
			System.out.println("usage: Extract (<old-directory> | <old-file>) <output-file>");
			System.out.println();
			System.out.println("<old-directory>    contains JS files with SDOC comments to be");
			System.out.println("                   extracted. This does not recursively descend");
			System.out.println("                   into all directories. Only files in the directory");
			System.out.println("                   itself will be processed");
			System.out.println("<old-file>         A JS file with SDOC comments to be extracted");
			System.out.println("<output-file>      The file where all SDOC comments will be placed");
		}
	}
	
	/**
	 * getComments
	 *
	 * @param file
	 * @return List
	 * @throws IOException 
	 */
	private static List<String> getComments(File file)
	{
		List<String> result = new ArrayList<String>();
		FileInputStream fis = null;
		
		try
		{
			fis = new FileInputStream(file);
			String text = FileUtils.getStreamText(fis);
			
			// search for comments that contain @alias
			int commentStart = text.indexOf(DOCUMENTATION_START);

			while (commentStart != -1)
			{
				int commentEnd = text.indexOf(DOCUMENTATION_END, commentStart);

				if (commentEnd != -1)
				{
					commentEnd += DOCUMENTATION_END.length();
					String commentText = text.substring(commentStart, commentEnd);

					// non-exhaustive sanity check to see if we have @-tags in the comment
					if (commentText.indexOf('@') != -1)
					{
						commentText = commentText.replaceAll("@name\\b", "@alias");
						result.add(commentText);
					}

					commentStart = text.indexOf(DOCUMENTATION_START, commentEnd);
				}
				else
				{
					commentStart = -1;
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("An error occurred while processing comments: " + e.getMessage());
		}
		finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					// fail silently
				}
			}
		}
		
		return result;
	}
}
