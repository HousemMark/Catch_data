package com.demo;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class PicDownloader {
	@Test
	public static void main(String[] args) {
		String path = "http://588ku.com/mb-zt/652.html";
		//调用获取源代码方法
		String code = getCodeByPath(path);
		//调用图片地址集合
		ArrayList<String> list = getListFromCode(code);
		for(String src : list) {
			save(src);
		}
		System.out.println("成功");
		
		
//		String a = "hdfhdij!ppp";
//		a.substring(0, );
		
	}
	
	/*
	 * 通过一个网址获取源代码 
	 */
	public static String getCodeByPath(String path) {
		//获取源代码
		String code = null;
		try {
			//获取URL
			code = Jsoup.connect(path).execute().body();
//			System.out.println(code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(code);
		return code;
	}
	
	/*
	 * 从源代码获取图片地址集合
	 */
	public static ArrayList<String> getListFromCode(String code){
		ArrayList<String> list = new ArrayList<>();
		//把源代码字符串转换成页面的对象
		Document doc = Jsoup.parse(code);
		//把所有的响应照片找到
		Elements elements = doc.select("img[class = lazy]");
//		Elements elements = doc.select("li.img-box img");
//		Elements elements = doc.getElementsByClass("lazy");
		for(int i = 0;i <elements.size();i++) {
			String s;
			s = elements.get(i).attr("data-original").toString();
			String a = s.replaceAll("!/fw/335/quality/90/unsharp/true/compress/true", "");
			System.out.println(a);
			list.add(a);
//			list.add(elements.get(i).attr("data-original"));
		}
		System.out.println(list);
		return list;
	}

	/*
	 * 将byte数组输出文件
	 */
	private static void save(String strUrl) {
		HttpURLConnection conn = null;
	    try {
	        URL url = new URL("http:"+strUrl);
	        conn = (HttpURLConnection) url.openConnection();
	        //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); //权限限制时可用此方法
	        conn.setRequestMethod("GET");
	        conn.setConnectTimeout(5*1000);
	        InputStream inputStream = conn.getInputStream();
	        byte[] btImg = readInputStream(inputStream);
	    }catch (Exception e){
	        e.printStackTrace();
	    }finally {
	        conn.disconnect();
	    }
	}
	
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	 
	    //输出文件流
	    OutputStream os = new FileOutputStream("d:\\abc/"+System.currentTimeMillis()+".png");
	 
	    int len = 0;
	    //写入
	    while ((len = inputStream.read(buffer)) != -1){
	        os.write(buffer,0,len);
	    }
	    //
	    while ((len = inputStream.read(buffer)) != -1){
	        outputStream.write(buffer,0,len);
	    }
	    os.close();
	    inputStream.close();
	    return outputStream.toByteArray();
	}
}

