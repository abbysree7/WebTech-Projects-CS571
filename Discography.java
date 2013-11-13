import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Discography extends HttpServlet{

	public Discography(){
		super();
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try{
		PrintWriter pw=resp.getWriter();
		resp.setContentType("text/html;charset=utf-8");
		String search=req.getParameter("search");
		String type=req.getParameter("type");
		String url="http://cs-server.usc.edu:18217/xmlgenerator.php?search="+search+"&type="+type;
		
			URL musicsearch=new URL(url);
		
		URLConnection urlcon=musicsearch.openConnection();
		BufferedReader br=new BufferedReader(new InputStreamReader(urlcon.getInputStream(),Charset.forName("UTF-8")));
		String in="",xmlin="";
		while((in=br.readLine())!=null){
			xmlin=xmlin+in;
		}
		//System.out.println(xmlin);
		br.close();
		//HTMLEntities.htmlentities(xmlin);
		//pw.write(xmlin);
		DocumentBuilderFactory db=DocumentBuilderFactory.newInstance();
		DocumentBuilder docbuild=db.newDocumentBuilder();
		Document doc=docbuild.parse(new InputSource(new StringReader(xmlin)));
		NodeList nodlist=doc.getElementsByTagName("result");
		if(nodlist.getLength()==0){
			pw.write("{\"results\": \"NULL\"}");
			pw.close();
		}
		else{
		String jsonstr="";
		jsonstr=jsonstr.concat("{\"results\":{");
		if(type.equals("artists")){
			jsonstr=jsonstr.concat("\"result\":[");	
			//System.out.println(jsonstr);
			//System.out.println(nodlist.item(0).getAttributes().getLength());
			for(int i=0;i<nodlist.getLength()-1;i++){
				String c=(nodlist.item(i).getAttributes().getNamedItem("cover").getNodeValue());
				String t=(nodlist.item(i).getAttributes().getNamedItem("name").getNodeValue());
				String g=(nodlist.item(i).getAttributes().getNamedItem("genre").getNodeValue());
				String y=(nodlist.item(i).getAttributes().getNamedItem("year").getNodeValue());
				String d=(nodlist.item(i).getAttributes().getNamedItem("details").getNodeValue());
				jsonstr=jsonstr.concat("{\"cover\":\""+c+"\", \"name\":\""+t+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}, ");
			}
			//System.out.println(jsonstr);
			String c=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("cover").getNodeValue());
			String t=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("name").getNodeValue());
			String g=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("genre").getNodeValue());
			String y=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("year").getNodeValue());
			String d=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("details").getNodeValue());
			jsonstr=jsonstr.concat("{\"cover\":\""+c+"\", \"name\":\""+t+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}]}}");
			//System.out.println(jsonstr);
			jsonstr=HTMLEntities.htmlentities(jsonstr);
			pw.write(jsonstr);
		}
		else if(type.equals("albums")){
			jsonstr=jsonstr.concat("\"result\":[");	
			//System.out.println(nodlist.item(0).getAttributes().getLength());
			for(int i=0;i<nodlist.getLength()-1;i++){
				String c=(nodlist.item(i).getAttributes().getNamedItem("cover").getNodeValue());
				String t=(nodlist.item(i).getAttributes().getNamedItem("title").getNodeValue());
				String a=(nodlist.item(i).getAttributes().getNamedItem("artist").getNodeValue());
				String g=(nodlist.item(i).getAttributes().getNamedItem("genre").getNodeValue());
				String y=(nodlist.item(i).getAttributes().getNamedItem("year").getNodeValue());
				String d=(nodlist.item(i).getAttributes().getNamedItem("details").getNodeValue());
				jsonstr=jsonstr.concat("{\"cover\":\""+c+"\", \"title\":\""+t+"\", \"artist\":\""+a+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}, ");
			}
			String c=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("cover").getNodeValue());
			String t=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("title").getNodeValue());
			String a=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("artist").getNodeValue());
			String g=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("genre").getNodeValue());
			String y=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("year").getNodeValue());
			String d=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("details").getNodeValue());
			jsonstr=jsonstr.concat("{\"cover\":\""+c+"\", \"title\":\""+t+"\", \"artist\":\""+a+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}]}}");
			jsonstr=HTMLEntities.htmlentities(jsonstr);
			pw.write(jsonstr);
		}
		else if(type.equals("songs")){
			jsonstr=jsonstr.concat("\"result\":[");	
			//System.out.println(nodlist.item(0).getAttributes().getLength());
			for(int i=0;i<nodlist.getLength()-1;i++){
				String c=(nodlist.item(i).getAttributes().getNamedItem("sample").getNodeValue());
				String t=(nodlist.item(i).getAttributes().getNamedItem("title").getNodeValue());
				String a=(nodlist.item(i).getAttributes().getNamedItem("performer").getNodeValue());
				String g=(nodlist.item(i).getAttributes().getNamedItem("composer").getNodeValue());
				String y=(nodlist.item(i).getAttributes().getNamedItem("song").getNodeValue());
				jsonstr=jsonstr.concat("{\"sample\":\""+c+"\", \"title\":\""+t+"\", \"performer\":\""+a+"\", \"composer\":\""+g+"\", \"song\":\""+y+"\"}, ");
			}
			String c=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("sample").getNodeValue());
			String t=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("title").getNodeValue());
			String a=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("performer").getNodeValue());
			String g=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("composer").getNodeValue());
			String y=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("song").getNodeValue());
			jsonstr=jsonstr.concat("{\"sample\":\""+c+"\", \"title\":\""+t+"\", \"performer\":\""+a+"\", \"composer\":\""+g+"\", \"song\":\""+y+"\"}]}}");
			//System.out.println(jsonstr);
			jsonstr=HTMLEntities.htmlentities(jsonstr);
			pw.write(jsonstr);
		}
		}
		}
		catch (Exception e) {
			PrintWriter pw=resp.getWriter();
			resp.setContentType("text/html;charset=utf-8");
			StringWriter sw = new StringWriter();
			PrintWriter tw = new PrintWriter(sw);
			e.printStackTrace(tw);	
			pw.println("		");
			pw.print(sw.toString());
			pw.println("		");		
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		try{
			PrintWriter pw=resp.getWriter();
			resp.setContentType("text/html;charset=utf-8");
			String search=req.getParameter("search");
			String type=req.getParameter("type");
			String url="http://cs-server.usc.edu:18217/xmlgenerator.php?search="+search+"&type="+type;
			URL musicsearch=new URL(url);
			URLConnection urlcon=musicsearch.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(urlcon.getInputStream(),Charset.forName("UTF-8")));
			String in="",xmlin="";
			while((in=br.readLine())!=null){
				xmlin=xmlin+in;
			}
			//System.out.println(xmlin);
			br.close();
			pw.write(xmlin);
			DocumentBuilderFactory db=DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuild=db.newDocumentBuilder();
			Document doc=docbuild.parse(new InputSource(new StringReader(xmlin)));
			NodeList nodlist=doc.getElementsByTagName("result");
			String jsonstr="";
			jsonstr=jsonstr.concat("{\"results\":{");
			if(type.equals("artists")){
				jsonstr=jsonstr.concat("\"result\":[");	
				//System.out.println(jsonstr);
				//System.out.println(nodlist.item(0).getAttributes().getLength());
				for(int i=0;i<nodlist.getLength()-1;i++){
					String c=(nodlist.item(i).getAttributes().getNamedItem("cover").getNodeValue());
					String t=(nodlist.item(i).getAttributes().getNamedItem("name").getNodeValue());
					String g=(nodlist.item(i).getAttributes().getNamedItem("genre").getNodeValue());
					String y=(nodlist.item(i).getAttributes().getNamedItem("year").getNodeValue());
					String d=(nodlist.item(i).getAttributes().getNamedItem("details").getNodeValue());
					jsonstr=jsonstr.concat("{\" cover\":\""+c+"\", \" name\":\""+t+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}, ");
				}
				//System.out.println(jsonstr);
				String c=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("cover").getNodeValue());
				String t=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("name").getNodeValue());
				String g=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("genre").getNodeValue());
				String y=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("year").getNodeValue());
				String d=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("details").getNodeValue());
				jsonstr=jsonstr.concat("{\"cover\":\""+c+"\", \"name\":\""+t+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}]}}");
				//System.out.println(jsonstr);
				jsonstr=HTMLEntities.htmlentities(jsonstr);
				pw.write(jsonstr);
			}
			else if(type.equals("albums")){
				jsonstr=jsonstr.concat("\"result\":[");	
				//System.out.println(nodlist.item(0).getAttributes().getLength());
				for(int i=0;i<nodlist.getLength()-1;i++){
					String c=(nodlist.item(i).getAttributes().getNamedItem("cover").getNodeValue());
					String t=(nodlist.item(i).getAttributes().getNamedItem("title").getNodeValue());
					String a=(nodlist.item(i).getAttributes().getNamedItem("artist").getNodeValue());
					String g=(nodlist.item(i).getAttributes().getNamedItem("genre").getNodeValue());
					String y=(nodlist.item(i).getAttributes().getNamedItem("year").getNodeValue());
					String d=(nodlist.item(i).getAttributes().getNamedItem("details").getNodeValue());
					jsonstr=jsonstr.concat("{\"cover\":\""+c+"\", \"title\":\""+t+"\", \"artist\":\""+a+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}, ");
				}
				String c=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("cover").getNodeValue());
				String t=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("title").getNodeValue());
				String a=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("artist").getNodeValue());
				String g=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("genre").getNodeValue());
				String y=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("year").getNodeValue());
				String d=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("details").getNodeValue());
				jsonstr=jsonstr.concat("{\"cover\":\""+c+"\", \"title\":\""+t+"\", \"artist\":\""+a+"\", \"genre\":\""+g+"\", \"year\":\""+y+"\", \"details\":\""+d+"\"}]}}");
				jsonstr=HTMLEntities.htmlentities(jsonstr);
				pw.write(jsonstr);
			}
			else if(type.equals("songs")){
				jsonstr=jsonstr.concat("\"result\":[");	
				//System.out.println(nodlist.item(0).getAttributes().getLength());
				for(int i=0;i<nodlist.getLength()-1;i++){
					String c=(nodlist.item(i).getAttributes().getNamedItem("sample").getNodeValue());
					String t=(nodlist.item(i).getAttributes().getNamedItem("title").getNodeValue());
					String a=(nodlist.item(i).getAttributes().getNamedItem("performer").getNodeValue());
					String g=(nodlist.item(i).getAttributes().getNamedItem("composer").getNodeValue());
					String y=(nodlist.item(i).getAttributes().getNamedItem("song").getNodeValue());
					jsonstr=jsonstr.concat("{\"sample\":\""+c+"\", \"title\":\""+t+"\", \"performer\":\""+a+"\", \"composer\":\""+g+"\", \"song\":\""+y+"\"}, ");
				}
				String c=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("sample").getNodeValue());
				String t=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("title").getNodeValue());
				String a=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("performer").getNodeValue());
				String g=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("composer").getNodeValue());
				String y=(nodlist.item(nodlist.getLength()-1).getAttributes().getNamedItem("song").getNodeValue());
				jsonstr=jsonstr.concat("{\"sample\":\""+c+"\", \"title\":\""+t+"\", \"performer\":\""+a+"\", \"composer\":\""+g+"\", \"song\":\""+y+"\"}]}}");
				//System.out.println(jsonstr);
				jsonstr=HTMLEntities.htmlentities(jsonstr);
				pw.write(jsonstr);
			}
			
		}
		catch (Exception e) {
			PrintWriter pw=resp.getWriter();
			resp.setContentType("text/html;charset=utf-8");
			StringWriter sw = new StringWriter();
			PrintWriter tw = new PrintWriter(sw);
			e.printStackTrace(tw);	
			pw.println("		");
			pw.print(sw.toString());
			pw.println("		");		
		}
		}
	

}
