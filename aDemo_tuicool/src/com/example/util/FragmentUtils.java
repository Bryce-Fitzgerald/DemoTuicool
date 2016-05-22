package com.example.util;


import com.example.tuicool.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtils
{

	public static void replaceFragment(FragmentActivity activity,Fragment mFragment)
	{
		if (mFragment == null)
		{
			return;
		}
		FragmentManager fm = activity.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		// ��Ӳ���:�����е�Fragment �� Ҫ�滻��Fragment
		ft.replace(R.id.container, mFragment);
		ft.commit();
	}
}
