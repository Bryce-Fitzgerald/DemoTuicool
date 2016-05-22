package com.example.util;

import com.example.model.detail.Article;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.VolleyListener;

import android.content.Context;

// ���泣�� 
//��̬�ڲ��� ---> ������� �����Ż����� 
public class TKConstants
{
	public static final String SP_THEME_TOGGLE = "themeToggleData"; // 
	public static final int ZUIXIAO = 0;
	public static final int PIANXIAO = 1;
	public static final int ZHONGDENG = 2;
	public static final int PIANDA = 3;
	public static final int ZUIDA = 4;
	
	public static final class Url
	{
//		public static final String HOST = "http://192.168.0.103:8080/tuiku_server/";
		// ��ţ�ƶ˴洢����
		public static final String HOST = "http://7xt68a.com1.z0.glb.clouddn.com/";
		public static final String HOT = HOST + "tuiku_hot.txt"; // ������Ŀ

		public static final String TUIJIAN = HOST + "tuiku_hot.txt"; // �Ƽ���Ŀ
		public static final String KEJI = HOST + "keji.txt"; 	// �Ƽ���Ŀ
		public static final String CHUANGYE = HOST + "tuiku_chaungye.txt"; // ��ҵ��Ŀ
		public static final String SHUMA = HOST + "shuma.txt"; // ������Ŀ
		public static final String JISHU = HOST + "jishu.txt"; // ������Ŀ
		public static final String SHEJI = HOST + "sheji.txt"; // �����Ŀ
		public static final String YINGXIAO = HOST + "yingxiao.txt"; // Ӫ����Ŀ
		
		public static final String ZHANDIAN = HOST + "zhandian.txt"; // վ��
		public static final String ZHUTI = HOST + "zhuti.txt"; // ����
		public static final String ZHOUKAN = HOST + "zhoukan.txt"; // �ܿ�
		
		public static final String HOT_DETAIL = HOST + "hot_detail.txt"; // ��������ҳ
		public static final String UPGRADE_LOG = HOST + "upgradelog.txt"; // ������־
		
	}

	public static final class bundleKey
	{
		public static final String FRAGMENT_TYPE = "FRAGMENT_TYPE"; // ��ת�����keyֵ

	}

	public static final class intentKey
	{
		public static final String DETAIL_URL = "detail_url";
		public static final String ARTICLE_FAVOR = "article_favor"; // ����ղ�keyֵ
	}

	public static final class fontSizeKey
	{
		public static final String DETAIL_FONT_SIZE = "detail_font_size"; // ���������С��Keyֵ

	}
	
	public static final class Opinion
	{
		public static final String OPINION_TABLE = "Feedback";
		public static final String CURRENTTIME = "currenttime";
		public static final String OPTIONINFO = "info";
	}
	
}
