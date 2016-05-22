package com.example.database_utils;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.model.Article;
import com.example.model.upgradelog.Item;
import com.example.model.upgradelog.Upgradelog;

public final class DBUtils
{
	public DBUtils(){};
	//����ղ�
	public static void insert(Article mArticle)
	{
		// ����һ���¶�����ԭ������ͬ������ ��ΪModelʵ��Cloneable�ӿ�,��дclone()�����������ظ���Ӻ�ȡ���ղ�
		Article newArticle = mArticle.clone();
		newArticle.save();
	}
	// ȡ���ղ�
	public static void delete(String param,Article mArticle)
	{
		new Delete().from(Article.class)
		.where(param, mArticle.getArticleId()).executeSingle();
	}
	// ��ѯ�Ƿ����ղ�
	public static boolean hasFavor(String param,Article mArticle)
	{
		Article article = new Select().from(Article.class)
				.where(param, mArticle.getArticleId()).executeSingle();
		return article != null;
	}
	//��ѯ�����ղ�
	public static List<Article> queryFavor()
	{
		return new Select().from(Article.class).execute();
	}
	
	// ���������־��
	public static void insertUpgradeLog(Item item)
	{
		Item newItem = new Item();
		newItem.save();
	}
	//��ѯ���µ���־
		public static List<Item> queryUpgradeLog()
		{
			return new Select().from(Item.class).execute();
		}
}
