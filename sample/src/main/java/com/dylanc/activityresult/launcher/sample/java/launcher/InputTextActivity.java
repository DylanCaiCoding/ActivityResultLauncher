/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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