import java.io.*;

public class BTest
{
	public static void main(String args[])
	{
		int i=0, e=0;
		try
		{
			for(int j=1;j<=9;j++)
			{
				i=0; e=0;
				String fileName ="C:\\projects\\PnineyHalacha\\pniney-tools\\ChangeFilesForPH\\src\\giyur_"+j+".html";
				File file = new File(fileName);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = "", oldtext = "", s1="<h1", s2="<h2", s3="name=\"_ftnref", s4="<a", s5="</a>";
				String buttonStr1 = "<button type=\"button\" id=\"";
				String buttonStr2 = "\" onclick=\"ok.performClick(id);\">הערה ";
				//String buttonStr2 = "\" onclick=\"ok.performClick(id);\">note ";/*For English books*/
				//String buttonStr2 = "\" onclick=\"ok.performClick(id);\">Remarque ";/*For French books*/
				//String buttonStr2 = "\" onclick=\"ok.performClick(id);\">nota ";/*For Spanish books*/
				String buttonStr3 = "</button>";
				String buttonStr = "";

				/*insert anchors*/
				while((line = reader.readLine()) != null)
				{
					if(line.indexOf(s1)!=-1)
					{
						line = line.replaceAll(s1, "<a id=\"0\"></a><h1");
					}
					if(line.indexOf(s2)!=-1)
					{
						i++;
						line = line.replaceAll(s2, "<a id=\"" + i + "\"></a><h2");
					}
					/*For adding /h2 for every h2*/
				/*	if((line.indexOf("</p>")!=-1) && (i==e+1))
					{
						e++;
						line = line.replaceAll("</p>", "</h2>");
					}*/

					oldtext += line + "\r\n";
				}

				/*change notes to buttons*/
				int index=0, start=0, end=0, noteId=1001, Heaara=1;
				while((oldtext.indexOf(s3, index)!=-1))
				{
					index = oldtext.indexOf(s3, index);
					start = oldtext.lastIndexOf(s4, index);
					end   = oldtext.indexOf(s5, index) + s5.length();//+5 because I want the index of after the note
					buttonStr = buttonStr1 + noteId + buttonStr2 + Heaara + buttonStr3;
					oldtext = oldtext.substring(0, start) + buttonStr + oldtext.substring(end, oldtext.length());
					index = end;
					noteId++;
					Heaara++;
				}

				/*hide notes*/
				String s6="<div>";
				oldtext = oldtext.replaceAll(s6, "<div style=\"display:none;\">");

				reader.close();
				// replace a word in a file
				//String newtext = oldtext.replaceAll("drink", "Love");

				//To replace a line in a file
				//   String newtext = oldtext.replace("<h2 dir=\"RTL\">", "<a id=\"1\"></a><h2 dir=\"RTL\">");
				// String newtext = oldtext.replaceAll("<h2 dir=\"RTL\">", "<a id=\"1\"></a><h2 dir=\"RTL\">");

				FileWriter writer = new FileWriter(fileName);
				writer.write(oldtext);
				writer.close();     		 
			}

		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}