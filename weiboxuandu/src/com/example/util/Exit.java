package com.example.util;

import com.example.R;
import com.example.logic.MainService;
import com.example.ui.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Exit {
	public static void btexit(final Context context) {
		new AlertDialog.Builder(context).setTitle(R.string.exit_app)
			.setIcon(R.drawable.dialog_info).setMessage(R.string.question_exit)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						System.out.println(" hello world ...........................................................................................");
						AccessTokenKeeper.clear(MyContext.getContext());
						// TODO Auto-generated method stub
						
						MainService.exitAPP(context);
						
					}
				}).setNegativeButton(R.string.cancle, null).create().show();
			
	}

}
