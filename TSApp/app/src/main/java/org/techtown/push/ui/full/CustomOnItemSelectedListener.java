package org.techtown.push.ui.full;

import android.view.View;
import android.widget.AdapterView;

public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {


    // 스피너에서 아이템이 선택됐을 때 토스트메세지로 출력
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}