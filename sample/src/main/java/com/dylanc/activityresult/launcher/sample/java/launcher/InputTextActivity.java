package com.dylanc.activityresult.launcher.sample.java.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.activityresult.launcher.sample.databinding.ActivityInputTextBinding;

/**
 * @author Dylan Cai
 */
public class InputTextActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityInputTextBinding binding = ActivityInputTextBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    ActionBar supportActionBar = getSupportActionBar();
    if (supportActionBar != null) {
      supportActionBar.setDisplayHomeAsUpEnabled(true);
      supportActionBar.setDisplayShowHomeEnabled(true);
    }
    setTitle(getIntent().getStringExtra(InputTextResultContract.KEY_TITLE));
    binding.tvName.setText(getIntent().getStringExtra(InputTextResultContract.KEY_NAME));
    binding.edtValue.setText(getIntent().getStringExtra(InputTextResultContract.KEY_VALUE));
    binding.btnSave.setEnabled(!binding.edtValue.getText().toString().isEmpty());
    binding.edtValue.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        binding.btnSave.setEnabled(!binding.edtValue.getText().toString().isEmpty());
      }
    });
    binding.btnSave.setOnClickListener((view) -> {
      Intent intent = new Intent();
      intent.putExtra(InputTextResultContract.KEY_VALUE, binding.edtValue.getText().toString());
      setResult(RESULT_OK, intent);
      finish();
    });
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }
}
