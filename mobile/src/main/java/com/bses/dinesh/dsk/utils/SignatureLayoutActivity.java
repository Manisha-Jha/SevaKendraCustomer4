package com.bses.dinesh.dsk.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bses.dinesh.dsk.R;
import com.bses.dinesh.dsk.fragMain.PhotosAndID;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SignatureLayoutActivity extends Activity {
	LinearLayout mContent;
	Signature mSignature;
	Button mClear, mGetSign, mCancel;
	public static String tempDir;
	public int count = 1;
	public String current = null;
	private Bitmap mBitmap;
	View mView;
	public static File mypath;
	private String uniqueId;
	String imageName ="";
	String activityName="";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_digi_signature);


			if (savedInstanceState == null) {
				Bundle extras = getIntent().getExtras();
				if(extras == null) {
					activityName= null;
				} else {
					activityName= extras.getString("activityName");
				}
			} else {
				activityName= (String) savedInstanceState.getSerializable("activityName");
			}
			
			/* ActionBar actionBar = getActionBar();
			 actionBar.setTitle("");*/

			tempDir = Environment.getExternalStorageDirectory() + "/"
					+ getResources().getString(R.string.external_dir) + "/";
			ContextWrapper cw = new ContextWrapper(getApplicationContext());
			File directory = cw.getDir(
					getResources().getString(R.string.external_dir),
					Context.MODE_PRIVATE);
			prepareDirectory();
			uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_"+"site"
					+ Math.random();
			current = uniqueId + ".png";
			mypath = new File(directory, current);
			mContent = (LinearLayout) findViewById(R.id.digiSignLayout);
			mSignature = new Signature(this, null);
			mSignature.setBackgroundColor(Color.WHITE);
			mContent.addView(mSignature, LayoutParams.FILL_PARENT,
					LayoutParams.MATCH_PARENT);
			mClear = (Button) findViewById(R.id.clearBtn);
			mGetSign = (Button) findViewById(R.id.captureBtn);
			mGetSign.setEnabled(false);
			mCancel = (Button) findViewById(R.id.cancelBtn);
//	merasor = (Button) findViewById(R.id.erasorBtn);
			mView = mContent;

			mClear.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("log_tag", "Panel Cleared");
					mSignature.clear();
				//	mGetSign.setEnabled(false);
				}
			});
			mGetSign.setOnClickListener(new OnClickListener() {
				@SuppressLint("NewApi")
				public void onClick(View v) {
					Log.v("log_tag", "Panel Saved");
					boolean error = captureSignature();
					if (!error) {
						mView.setDrawingCacheEnabled(true);
						mSignature.save(mView);
						
						try {
							FileInputStream mFileInputStream = new FileInputStream(
									imageName);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							byte[] b = new byte[1024];
							int bytesRead = 0;
							while ((bytesRead = mFileInputStream.read(b)) != -1) {
								bos.write(b, 0, bytesRead);
							}
							byte[] ba = bos.toByteArray();

							String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

							Bitmap bitmap = BitmapFactory.decodeByteArray(ba, 0,ba.length);


							Drawable d = new BitmapDrawable(getResources(),bitmap);


							if(activityName.equalsIgnoreCase("OtherDetails")) {
								PhotosAndID.setSignatureImage(bitmap, d);
							}

							//OtherDetails.getInstance().signature.setBackground(d);
							
						} catch (FileNotFoundException exception) {
							System.out.println("..........file not found");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(""+ e.getMessage());
							e.printStackTrace();
						}
						finish();
					}
				}
			});

			mCancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("log_tag", "Panel Canceled");
					Bundle b = new Bundle();
					b.putString("status", "cancel");
					Intent intent = new Intent();
					intent.putExtras(b);
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }
 
 
 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        super.onOptionsItemSelected(item);
 
        switch(item.getItemId()){
        
            case R.id.video:
                Toast.makeText(getBaseContext(), "You selected Video", Toast.LENGTH_SHORT).show();
                break;
 
            case R.id.email:
                Toast.makeText(getBaseContext(), "You selected EMail", Toast.LENGTH_SHORT).show();
                break;
 
        }
        return true;
 
    }*/

	@Override
	protected void onDestroy() {
		Log.w("GetSignature", "onDestory");
		super.onDestroy();
	}

	private boolean captureSignature() {
		boolean error = false;
		
			String errorMessage = "";

			if (error) {
				Toast toast = Toast
						.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP, 105, 50);
				toast.show();
			}
		
		return error;
	}

	private String getTodaysDate() {
		final Calendar c = Calendar.getInstance();
		int todaysDate = (c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ (c.get(Calendar.DAY_OF_MONTH));
		Log.w("DATE:", String.valueOf(todaysDate));
		return (String.valueOf(todaysDate));
	}

	private String getCurrentTime() {
		final Calendar c = Calendar.getInstance();
		int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)
				+ (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
		Log.w("TIME:", String.valueOf(currentTime));
		return (String.valueOf(currentTime));

	}

	@SuppressLint("WrongConstant")
	private boolean prepareDirectory() {
		try {
			if (makedirs()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(
					this,
					"Could not initiate File System.. Is Sdcard mounted properly?",
					1000).show();
			return false;
		}
	}

	private boolean makedirs() {
		File tempdir = new File(Environment.getExternalStorageDirectory()+ "/Image");
		
			if (!tempdir.exists())
				tempdir.mkdirs();
			if (tempdir.isDirectory()) {
				File[] files = tempdir.listFiles();
				
			}
		
		return (tempdir.isDirectory());
	}

	public class Signature extends View {
		private static final float STROKE_WIDTH = 5f;
		private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		private Paint paint = new Paint();
		private Path path = new Path();
		private float lastTouchX;
		private float lastTouchY;
		private final RectF dirtyRect = new RectF();
		ArrayList<CoOrdinates> coOrdinatesList= new ArrayList<CoOrdinates>();

		public Signature(Context context, AttributeSet attrs) {
			super(context, attrs);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(STROKE_WIDTH);
		}

		public Bitmap getSign(View v) {
			Log.v("log_tag", "Width: " + v.getWidth());
			Log.v("log_tag", "Height: " + v.getHeight());
			if (mBitmap == null) {
				mBitmap = Bitmap.createBitmap(mContent.getWidth(),
						mContent.getHeight(), Bitmap.Config.RGB_565);
				;
			}
			return mBitmap;
		}

		public void save(View v) {
			Log.v("log_tag", "Width: " + v.getWidth());
			Log.v("log_tag", "Height: " + v.getHeight());
			if (mBitmap == null) {
				mBitmap = Bitmap.createBitmap(mContent.getWidth(),
						mContent.getHeight(), Bitmap.Config.RGB_565);
				
				Canvas canvas = new Canvas(mBitmap);
				v.draw(canvas);
			}
			Canvas canvas = new Canvas(mBitmap);
			try {
				File storagePath = new File(
						Environment.getExternalStorageDirectory()+ "/Image");
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
				.format(new Date());
				imageName = storagePath.getPath() + File.separator + "IMG_"
						+ timeStamp + "_" + ".png";
				FileOutputStream mFileOutStream = new FileOutputStream(imageName);
				v.draw(canvas);
				mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
				mFileOutStream.flush();
				mFileOutStream.close();
				String url = Images.Media.insertImage(getContentResolver(),
						mBitmap, "title", null);
				Log.v("log_tag", "url: " + url);
				
			} catch (Exception e) {
				Log.v("log_tag", e.toString());
			}
		}

		public void clear() {
			
				path.reset();
				/*invalidate();*/
				System.out.println("............len before = "+ coOrdinatesList.size());
				if(coOrdinatesList.size()>10){
					for(int i =0;i<=10;i++){
						int x = coOrdinatesList.size()-1;
						coOrdinatesList.remove(x);
						System.out.println("........................x = "+ x);
					}
				}
				else{
					coOrdinatesList = new ArrayList<CoOrdinates>();
				}
				System.out.println("............len after = "+ coOrdinatesList.size());
				//path = new Path();
				for(int j=0; j<coOrdinatesList.size();j++){
					if(coOrdinatesList.get(j).isLineStart()==true){
						path.moveTo(coOrdinatesList.get(j).getX(), coOrdinatesList.get(j).getY());
					}else{
						path.lineTo(coOrdinatesList.get(j).getX(), coOrdinatesList.get(j).getY());
					}
				}
				invalidate();
			
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
		
				float eventX = event.getX();
				float eventY = event.getY();
				mGetSign.setEnabled(true);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					path.moveTo(eventX, eventY);
					lastTouchX = eventX;
					lastTouchY = eventY;
					CoOrdinates coOrdinates= new CoOrdinates();
					coOrdinates.setX(lastTouchX);
					coOrdinates.setY(lastTouchY);
					coOrdinates.setLineStart(true);
					coOrdinatesList.add(coOrdinates);
					return true;
				case MotionEvent.ACTION_MOVE:
				case MotionEvent.ACTION_UP:
					resetDirtyRect(eventX, eventY);
					int historySize = event.getHistorySize();
					for (int i = 0; i < historySize; i++) {
						float historicalX = event.getHistoricalX(i);
						float historicalY = event.getHistoricalY(i);
						expandDirtyRect(historicalX, historicalY);
						expandDirtyRect(historicalX, historicalY);
						CoOrdinates coOrdinates1= new CoOrdinates();
						coOrdinates1.setX(historicalX);
						coOrdinates1.setY(historicalY);
						coOrdinates1.setLineStart(false);
						coOrdinatesList.add(coOrdinates1);
						path.lineTo(historicalX, historicalY);
					}
					path.lineTo(eventX, eventY);
					break;
				default:
					debug("Ignored touch event: " + event.toString());
					return false;
				}
				invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
						(int) (dirtyRect.top - HALF_STROKE_WIDTH),
						(int) (dirtyRect.right + HALF_STROKE_WIDTH),
						(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
				lastTouchX = eventX;
				lastTouchY = eventY;
			
			return true;
		}

		private void debug(String string) {

		}

		private void expandDirtyRect(float historicalX, float historicalY) {
			
				if (historicalX < dirtyRect.left) {
					dirtyRect.left = historicalX;
				} else if (historicalX > dirtyRect.right) {
					dirtyRect.right = historicalX;
				}
				if (historicalY < dirtyRect.top) {
					dirtyRect.top = historicalY;
				} else if (historicalY > dirtyRect.bottom) {
					dirtyRect.bottom = historicalY;
				}
			
		}

		private void resetDirtyRect(float eventX, float eventY) {
			
				dirtyRect.left = Math.min(lastTouchX, eventX);
				dirtyRect.right = Math.max(lastTouchX, eventX);
				dirtyRect.top = Math.min(lastTouchY, eventY);
				dirtyRect.bottom = Math.max(lastTouchY, eventY);
			
		}

	}

	public Bitmap decodeBase64(String input) {
		byte[] decodeByte = Base64.decode(input, MODE_WORLD_READABLE);
		System.out.println("....1");
		return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
	}

}
