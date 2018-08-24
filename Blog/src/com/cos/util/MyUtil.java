package com.cos.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.cos.dto.BoardVO;

public class MyUtil {
	public static String getReplace(String code) {
		return code.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll("\n", "<br>");
	}

	// 모든 HTML 태그 제거
	public static String removeTag(String html) {
		html = html.replaceAll("&nbsp;", " ");
		return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	}

	public static String preview(String content) {
		Document doc = Jsoup.parse(content);
		Elements img_tag = doc.select("img");
		Elements iframe_tag = doc.select("iframe");
		String remove_content = removeTag(content);
		if (remove_content.length() == 0) {
			if (img_tag.size() > 0 && iframe_tag.size() > 0) {
				remove_content = "본문에 이미지와 영상만 존재합니다.";
			} else if (img_tag.size() > 0 && iframe_tag.size() == 0) {
				remove_content = "본문에 이미지만 존재합니다.";
			} else if (img_tag.size() == 0 && iframe_tag.size() > 0) {
				remove_content = "본문에 영상만 존재합니다.";
			} else {
				remove_content = "본문에 내용이 존재하지 않습니다.";
			}
		}
		// 한줄에 영어는 73글자, 한글은 45글자
		if (remove_content.length() > 43) {
			remove_content = remove_content.substring(0, 43);
		}
		return remove_content;
	}

	public static String makeYoutube(String content) {
		Document doc = Jsoup.parse(content);
		Elements a_tag = doc.select("a");
		String iFrame = "";

		for (Element item : a_tag) {
			if (item.attr("href").contains("www.youtube.com/watch")) {
				System.out.println(item.attr("윹튶 영상링크 존재"));
				String temp = item.attr("href");
				String[] split = temp.split("=");
				String value = split[1];
				value = value.substring(0, 11); // 영상 재생시간에 따른 추가 문자 제거
				iFrame = "<iframe id=\"player\" type=\"text/html\" width=\"90%\" height=\"409\" src=\"http://www.youtube.com/embed/"
						+ value
						+ "\" frameborder=\"0\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" allowfullscreen=\"\"></iframe>";
				item.after(iFrame);

			}
		}
		return doc.toString();
	}

	@Test
	public void makeYoutube() {
		String content = "<a href=\"https://www.youtube.com/watch?v=vfD96yRT8cs\" target=\"_self\">https://www.youtube.com/watch?v=vfD96yRT8cs</a><br>";
		Document doc = Jsoup.parse(content);
		Elements a_tag = doc.select("a");
		String iFrame = "";
		System.out.println("1");
		for (Element item : a_tag) {
			System.out.println(3);
			if (item.attr("href").contains("https://www.youtube.com/watch")) {
				System.out.println(4);
				System.out.println(item.attr("윹튶 영상링크 존재"));
				String temp = item.attr("href");
				String[] split = temp.split("=");
				System.out.println(split[1]);
				String value = split[1];
				value = value.substring(0, 11); // 영상 재생시간에 따른 추가 문자 제거
				iFrame = "<iframe id=\"player\" type=\"text/html\" width=\"90%\" height=\"409\" src=\"http://www.youtube.com/embed/"
						+ value
						+ "\" frameborder=\"0\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" allowfullscreen=\"\"></iframe>";
				item.after(iFrame);
			}
		}
		System.out.println("2");
		
	}
	
	   //HotPost 변경유무 확인 (데이터 조회, 수정, 삭제시 호출됨)
	   public static boolean getBoardChange(List<BoardVO> hotpost1, List<BoardVO> hotpost2) {
	      boolean change = false;
	      for(int i=0; i< hotpost1.size(); i++) {
	         if(hotpost1.get(i).getNum() != hotpost2.get(i).getNum()) change = true;
	         if(!hotpost1.get(i).getTitle().equals(hotpost2.get(i).getTitle())) change = true;
	         if(hotpost1.get(i).getReadcount() != hotpost2.get(i).getReadcount()) change = true;
	      }
	      return change;
	   }
}
