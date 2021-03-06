package com.kingtone.jw.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingtone.jw.service.domain.QueryEnvelop;
import com.kingtone.jw.service.domain.SynchEnvelop;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlParseTool {
	/**
	 * 对象转换成xml
	 * @param obj
	 * @return
	 */
	public static String toXml(Object obj) {
		XStream xs = new XStream();
		return xs.toXML(obj);
	}
	
	//字符串转换成对象
	public static Object toObj(String xmlstr,Object obj){
        XStream xs = new XStream(new DomDriver());
        xs.fromXML(xmlstr, obj);
        return obj;
	}
	
	public static void main(String[] args){
//		Map map = new HashMap<String, String>();
//		map.put("MOBILE", "18028383710");
//		QueryEnvelop qe = new QueryEnvelop();
//		qe.setName("zhangsan");
//		qe.setCondition(map);
//		System.out.println("-------------"+toXml(qe));
		
//		List list = new ArrayList();
//		CZPerson czperson = new CZPerson();
//		czperson.setId(1);
//		czperson.setUserId("1");
//		czperson.setPoliceId("11");
//		czperson.setUserName("中文");
//		list.add(czperson);
//		ServiceData sd = new ServiceData();
//		sd.setCzpersonList(list);
//		
//		String xmlstr = toXml(sd);
//		System.out.println(xmlstr);
//		
//		ServiceData sd2 = new ServiceData();
//		sd2 = (ServiceData)toObj(xmlstr,sd2); 
//		
//		System.out.println(11);
//		System.out.println(czperson2.getId()+"|"+czperson2.getPoliceId()+"|"+czperson2.getUserName());
		

	}
	/*解析数据
	private void test(String abc){
		abc=abc.replace("\n", "");
		abc=abc.replace("&apos;", "'");	
		SynchEnvelop sep=ParseSep(abc);
	}*/
	/*解析数据*/
	private int findFisrtIndex(String str,String lable){
		return str.indexOf("<"+lable+">")+lable.length()+2;
	}
	private int findLastIndex(String str,String lable){
		return  str.lastIndexOf("</"+lable+">");
	}
	private String findSubStr(String str,String lable){
		return str.substring(findFisrtIndex(str,lable),findLastIndex(str,lable));
	}
	/*解析数据*/
	private String[] findSplStrs(String str,String lable,int num){
		String str_temp="</"+lable+">";
		for(int i=0;i<num+1;i++){
			str_temp=str_temp+"  ";
		}
		str_temp=str_temp+"<"+lable+">";
		String[] temp=str.split(str_temp);
		return str.split(str_temp);
	}

	/*解析数据*/
	private SynchEnvelop ParseSep(String str){
		String bizList="bizList";
		String bizComponentList="bizComponentList";
		String bizRelateList="bizRelateList";
		String resList="resList";
		String selCardList="selCardList";
		String strs[]={bizList,bizComponentList,bizRelateList,resList,selCardList};
		SynchEnvelop sep=new SynchEnvelop();
		sep.setBizList(findSep(str,bizList));
		sep.setBizComponentList(findSep(str,bizComponentList));
		sep.setBizRelateList(findSep(str,bizRelateList));
		sep.setResList(findSep(str,resList));
		sep.setSelCardList(findSep(str,selCardList));
		return sep;
	}

	String map="map";
	String entry="entry";
	String string="string";
	/*解析数据*/
	private List findSep(String str,String lable){
		String[] strs_lable={lable,map,entry,string};
		List resList_temp = new ArrayList();
		if(str.indexOf("<"+lable+"/>")>0)return resList_temp;
		parseSep(resList_temp,findSubStr(str,lable),strs_lable,null,0,true);	
		return resList_temp;
	}
	/*解析数据*/
	private void parseSep(List resList_temp,String str,String[] lable,Map map_temp,int num_temp,boolean first){
		num_temp++;
		int num=num_temp;
		String[] strs=findSplStrs(findSubStr(str,lable[num]),lable[num],num);
		for(int i=0;i<strs.length;i++){
			if(lable[num]==string) {
				if(strs.length==2)map_temp.put(strs[0], strs[1]);
				else map_temp.put(strs[0], "");
			}
			else{
				if(first) map_temp=new HashMap();
			
			parseSep(resList_temp,strs[i],lable,map_temp,num,false);
			}	
			if(first) resList_temp.add(map_temp);
		}	
		
	}
}
