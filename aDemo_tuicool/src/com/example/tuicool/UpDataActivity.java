package com.example.tuicool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.example.tuicool.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class UpDataActivity extends Activity
{
	//ģ��ӷ������л�õ��µİ汾��2
		int newversionCode =2;
		private int NOTIFY_ID = 1;
		// �ļ�����·��
		// sd������·��
		String saveSDFile =Environment.getExternalStorageDirectory() + "/DownloadApk";
		String fileName ="kugouplay.apk";
		String apkURL ="http://192.168.56.1:1230/kugouplay1.apk";
		private Notification notification;
		private NotificationManager manager;
		private int versionCode;
		private AnimationDrawable animationDrawable; 
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_up_data);
			ImageView img_fragment_animation = (ImageView) findViewById(R.id.img_fragment_animation);
			
			animationDrawable = (AnimationDrawable) img_fragment_animation.getDrawable();
			animationDrawable.start(); // ���Ŷ���
		}

		public void btnUpData(View v)
		{
			// �Ƿ���Ҫ���� Ҫ�жϵ�ǰ�汾���Ƿ�����== �Ƚϰ汾��
			try
			{
				// ��ð�������
				PackageManager packageManager = getPackageManager();
				// ��ð�����Ϣ
				PackageInfo info = packageManager.getPackageInfo(getPackageCodePath(), 0);
				// ��õ�ǰ�汾��
				versionCode = info.versionCode;
				String versionName = info.versionName; // ��õ�ǰ�汾��
				
			} catch (NameNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// �ж�  �����ǰ�汾С�����°汾 �͵���������ʾ
			if (versionCode < newversionCode)
			{
				showUpDataDialog(); // �������µĶԻ�����ʾ
			}
		}
		// �������µĶԻ�����ʾ
		private void showUpDataDialog()
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("������ʾ")
			.setMessage("1,������XXXXBUG \n2,������XXXX����")
			.setNegativeButton("�Ժ����", new OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					finish();
				}
			})
			.setPositiveButton("��������", new OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// ����֪ͨ
					sendNotification();
					////����apk�ļ�
					new APKTask().execute(apkURL);
				}
			});
			builder.show(); // �����Ի�����ʾ
		}
		
		// �����ļ����� ״̬��֪ͨ
		protected void sendNotification()
		{
			manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
			
			RemoteViews views = new RemoteViews(getPackageName(), R.layout.notify_updata_layout);
			Intent intent1 = new Intent(this, MainActivity.class);
			PendingIntent intent = PendingIntent.getActivity(this, 0, intent1 , 0);
			
			builder.setAutoCancel(true) //���õ����ȡ��
//			.setOngoing(true) //���õ������ȡ��
			.setTicker("��ʼ�����ļ�") // ���������������ı���ʾ��Ϣ
			.setSmallIcon(R.drawable.ic_download) //��������������ͼ��
//			.setSound(Uri.parse("")) // ��������������������ʾ
//			.setVibrate(new long[]{11,22,11,22}) // ������  Ҫ��Ȩ��
			.setContent(views) // ���ù�������
			.setContentIntent(intent); //������ʱ��ͼ
			
			notification = builder.build();
			// ����֪ͨ
			manager.notify(NOTIFY_ID, notification );
			
			
		}
		
//		apk �첽����
		class APKTask extends AsyncTask<String, Integer, String>
		{

			private FileOutputStream fos;

			@Override
			protected String doInBackground(String... params)
			{
				String str= "";
				try
				{
					// ���URL����
					URL url = new URL(params[0]);
					// ������
					URLConnection connection = url.openConnection();
					// ����ļ����ܳ���
					int length = connection.getContentLength();
					// ���������
					InputStream is = connection.getInputStream();
					File path = new File(saveSDFile);
					// �жϵ�ǰ�Ƿ����ļ��� 
					if (!path.exists()) // ���û���ļ��ʹ���һ���ļ���
					{
						path.mkdir(); // �����ھʹ���һ���ļ���
					}
					File file = new File(saveSDFile + fileName);
					fos = new FileOutputStream(file);
					int len =0;
					byte[] buffer = new byte[1024];
					int dlLength =0;
					int node=5120;//���ؽڵ�5kb
					int nodeNum=0;//�ڵ����
					while( -1 != (len = is.read(buffer )))
					{
						
						dlLength+=len;
						fos.write(buffer, 0, len);
						if (dlLength > node*(nodeNum))
						{
							nodeNum++;
							// ���͸��½���
							publishProgress(dlLength*100/length);
						}
					}
					// ������صĳ��ȵ����ļ����ܳ���
					if (dlLength == length)
					{
						publishProgress(100);
					}
					str = "���سɹ�";
				} catch (MalformedURLException e)
				{
					str = "����ʧ��";
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					str = "����ʧ��";
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if (fos != null)
					{
						try
						{
							fos.close();
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				return str;
			}
			
			// ���� ���ͽ��ȸ��µķ���  ���¿ؼ���ʾ
			@Override
			protected void onProgressUpdate(Integer... values)
			{
				// ��֪ͨ�ϸ��½���
				// �����ı�����
				notification.contentView.setTextViewText(R.id.textView1, "������"+ values[0] + "%");
				// ���ý���
				notification.contentView.setProgressBar(R.id.progressBar1, 100,// ����progressBar1�ܽ���
						values[0], // ���õ�ǰ����
						false); // �Ƿ���ʾ����
				
				// �ı�֪ͨ������ ��Ҫ����֪ͨ
				manager.notify(NOTIFY_ID, notification);
				
			super.onProgressUpdate(values);
			}
			
			@Override
			protected void onPostExecute(String result)
			{
				if (result.equals("���سɹ�"))
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					//ָ���ļ���·�����Լ��ļ����� mimeType:APK
					Uri data = Uri.fromFile(new File(saveSDFile + fileName));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setDataAndType(data,"application/vnd.android.package-archive" );
//					startActivity(intent ); // ֱ����ת��װ����
					
					//���״̬��֪ͨ��ת��װ����
					PendingIntent contentIntent = PendingIntent.getActivity(UpDataActivity.this, 0, intent, 0);
					notification.contentIntent =contentIntent;
					manager.notify(NOTIFY_ID, notification);
				}
				
			super.onPostExecute(result);
			}
			
		}
}
