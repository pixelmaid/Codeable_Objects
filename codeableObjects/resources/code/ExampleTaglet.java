/*
 * Codeable Objects by Jennifer Jacobs is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * Based on a work at hero-worship.com/portfolio/codeable-objects.
 *
 * This file is part of the Codeable Objects Framework.
 *
 *     Codeable Objects is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Codeable Objects is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Codeable Objects.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.sun.tools.doclets.Taglet;
import com.sun.javadoc.*;
import java.util.Map;
import java.io.*;
/**
 * A sample Taglet representing @example. This tag can be used in any kind of
 * {@link com.sun.javadoc.Doc}.  It is not an inline tag. The text is displayed
 * in yellow to remind the developer to perform a task.  For
 * example, "@example Hello" would be shown as:
 * <DL>
 * <DT>
 * <B>To Do:</B>
 * <DD><table cellpadding=2 cellspacing=0><tr><td bgcolor="yellow">Fix this!
 * </td></tr></table></DD>
 * </DL>
 *
 * @author Jamie Ho
 * @since 1.4
 */

public class ExampleTaglet implements Taglet {
    
    private static final String NAME = "example";
    private static final String HEADER = "example To Do:";

    /**
     * Return the name of this custom tag.
     */
    public String getName() {
        return NAME;
    }
    
    /**
     * Will return true since <code>@example</code>
     * can be used in field documentation.
     * @return true since <code>@example</code>
     * can be used in field documentation and false
     * otherwise.
     */
    public boolean inField() {
        return true;
    }

    /**
     * Will return true since <code>@example</code>
     * can be used in constructor documentation.
     * @return true since <code>@example</code>
     * can be used in constructor documentation and false
     * otherwise.
     */
    public boolean inConstructor() {
        return true;
    }
    
    /**
     * Will return true since <code>@example</code>
     * can be used in method documentation.
     * @return true since <code>@example</code>
     * can be used in method documentation and false
     * otherwise.
     */
    public boolean inMethod() {
        return true;
    }
    
    /**
     * Will return true since <code>@example</code>
     * can be used in method documentation.
     * @return true since <code>@example</code>
     * can be used in overview documentation and false
     * otherwise.
     */
    public boolean inOverview() {
        return true;
    }

    /**
     * Will return true since <code>@example</code>
     * can be used in package documentation.
     * @return true since <code>@example</code>
     * can be used in package documentation and false
     * otherwise.
     */
    public boolean inPackage() {
        return true;
    }

    /**
     * Will return true since <code>@example</code>
     * can be used in type documentation (classes or interfaces).
     * @return true since <code>@example</code>
     * can be used in type documentation and false
     * otherwise.
     */
    public boolean inType() {
        return true;
    }
    
    /**
     * Will return false since <code>@example</code>
     * is not an inline tag.
     * @return false since <code>@example</code>
     * is not an inline tag.
     */
    
    public boolean isInlineTag() {
        return false;
    }
    
    /**
     * Register this Taglet.
     * @param tagletMap  the map to register this tag to.
     */
    public static void register(Map tagletMap) {
       ExampleTaglet tag = new ExampleTaglet();
       Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }

    /**
     * Given the <code>Tag</code> representation of this custom
     * tag, return its string representation.
     * @param tag   the <code>Tag</code> representation of this custom tag.
     */
    public String toString(Tag tag) {
        return createHTML(readFile(tag.text()));
    }
     
    
    /**
     * Given an array of <code>Tag</code>s representing this custom
     * tag, return its string representation.
     * @param tags  the array of <code>Tag</code>s representing of this custom tag.
     */
    public String toString(Tag[] tags) {
        if (tags.length == 0) {
            return null;
        }
		return createHTML(readFile(tags[0].text()));
    }
    
    
    
    String createHTML(String theString) {
    	if(theString!=null) {
        String dd = "<script type=\"text/javascript\">\n" +
        			"<!--\n"+ 
        			"document.getElementsByTagName('html')[0].className = 'isjs';" +
					"function toggle(dt) { var display, dd=dt; do{ dd = dd.nextSibling } while(dd.tagName!='DD'); toOpen =!dd.style.display;" +
  					"dd.style.display = toOpen? 'block':''; dt.getElementsByTagName('span')[0].innerHTML  = toOpen? '-':'+' ; }\n" +
  					"-->\n</script>";

		return dd+"\n<div id=\"test\" class=\"toggleList\">" +
		"<dl><dt onclick=\"toggle(this);\"><span>+</span>Example</dt>" +
		"<dd><pre>"+theString+"</pre>" +
		"</dd></dl></div>";
		} 
    	return "";
    }
    
    
    /**
     * check if the examples directory exists and return the example as given in the tag.
     * @param theExample the name of the example
     */
     String readFile(String theExample) { 
		String record = "";
		String myResult = "";
		int recCount = 0;
		String myDir = "../examples";
		File file=new File(myDir);
		if(file.exists()==false) {
			myDir = "./examples";
		}
        try { 
			FileReader fr = new FileReader(myDir+"/"+theExample+"/"+theExample+".pde");
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			while ((record = br.readLine()) != null) {
				myResult += record+"\n";
			} 
		} catch (IOException e) { 
			System.out.println(e);
			return null;
		}
		return myResult;
     }
}


