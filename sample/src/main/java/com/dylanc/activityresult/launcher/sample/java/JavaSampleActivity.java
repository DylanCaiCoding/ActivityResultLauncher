package com.dylanc.activityresult.launcher.sample.java;

import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.OpenableColumns;

import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;

import com.dylanc.activityresult.launcher.AppDetailsSettingsLauncher;
import com.dylanc.activityresult.launcher.CreateDocumentLauncher;
import com.dylanc.activityresult.launcher.CropPictureLauncher;
import com.dylanc.activityresult.launcher.EnableBluetoothLauncher;
import com.dylanc.activityresult.launcher.EnableLocationLauncher;
import com.dylanc.activityresult.launcher.GetMultipleContentsLauncher;
import com.dylanc.activityresult.launcher.OpenDocumentLauncher;
import com.dylanc.activityresult.launcher.OpenDocumentTreeLauncher;
import com.dylanc.activityresult.launcher.OpenMultipleDocumentsLauncher;
import com.dylanc.activityresult.launcher.PickContactLauncher;
import com.dylanc.activityresult.launcher.PickContentLauncher;
import com.dylanc.activityresult.launcher.RequestMultiplePermissionsLauncher;
import com.dylanc.activityresult.launcher.RequestPermissionLauncher;
import com.dylanc.activityresult.launcher.TakePictureLauncher;
import com.dylanc.activityresult.launcher.TakePicturePreviewLauncher;
import com.dylanc.activityresult.launcher.TakeVideoLauncher;
import com.dylanc.activityresult.launcher.sample.BaseActivity;
import com.dylanc.activityresult.launcher.sample.R;
import com.dylanc.activityresult.launcher.sample.databinding.ActivityLauncherBinding;
import com.dylanc.activityresult.launcher.sample.java.launcher.InputTextLauncher;
import com.dylanc.activityresult.launcher.sample.widget.PictureDialogFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dylan Cai
 */
public class JavaSampleActivity extends BaseActivity {

  private final RequestPermissionLauncher requestPermissionLauncher = new RequestPermissionLauncher(this);
  private final RequestMultiplePermissionsLauncher requestMultiplePermissionsLauncher = new RequestMultiplePermissionsLauncher(this);
  private final AppDetailsSettingsLauncher appDetailsSettingsLauncher = new AppDetailsSettingsLauncher(this);
  private final TakePicturePreviewLauncher takePicturePreviewLauncher = new TakePicturePreviewLauncher(this);
  private final TakePictureLauncher takePictureLauncher = new TakePictureLauncher(this);
  private final PickContentLauncher pickContentLauncher = new PickContentLauncher(this);
  private final GetMultipleContentsLauncher getMultipleContentsLauncher = new GetMultipleContentsLauncher(this);
  private final CropPictureLauncher cropPictureLauncher = new CropPictureLauncher(this);
  private final TakeVideoLauncher takeVideoLauncher = new TakeVideoLauncher(this);
  private final CreateDocumentLauncher createDocumentLauncher = new CreateDocumentLauncher(this);
  private final OpenDocumentLauncher openDocumentLauncher = new OpenDocumentLauncher(this);
  private final OpenMultipleDocumentsLauncher openMultipleDocumentsLauncher = new OpenMultipleDocumentsLauncher(this);
  private final OpenDocumentTreeLauncher openDocumentTreeLauncher = new OpenDocumentTreeLauncher(this);
  private final PickContactLauncher pickContactLauncher = new PickContactLauncher(this);
  private final EnableBluetoothLauncher enableBluetoothLauncher = new EnableBluetoothLauncher(this);
  private final EnableLocationLauncher enableLocationLauncher = new EnableLocationLauncher(this);
  private final InputTextLauncher inputTextLauncher = new InputTextLauncher(this);

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityLauncherBinding binding = ActivityLauncherBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.btnRequestPermission.setOnClickListener((v) -> requestPermission());
    binding.btnRequestMultiplePermissions.setOnClickListener((v) -> requestMultiplePermissions());
    binding.btnAppDetailsSettings.setOnClickListener((v) -> goToAppDetailsSettings());
    binding.btnTakePicturePreview.setOnClickListener((v) -> takePicturePreview());
    binding.btnTakePicture.setOnClickListener((v) -> takePicture());
    binding.btnCropPicture.setOnClickListener((v) -> takePictureAndCropIt());
    binding.btnTakeVideo.setOnClickListener((v) -> takeVideo());
    binding.btnPickPicture.setOnClickListener((v) -> pickPicture());
    binding.btnPickVideo.setOnClickListener((v) -> pickVideo());
    binding.btnGetMultiplePicture.setOnClickListener((v) -> getMultiplePicture());
    binding.btnGetMultipleVideo.setOnClickListener((v) -> getMultipleVideo());
    binding.btnEnableBluetooth.setOnClickListener((v) -> enableBluetooth());
    binding.btnEnableLocation.setOnClickListener((v) -> enableLocation());
    binding.btnCreateDocument.setOnClickListener((v) -> createDocument());
    binding.btnOpenDocument.setOnClickListener((v) -> openDocument());
    binding.btnOpenMultipleDocument.setOnClickListener((v) -> openMultipleDocuments());
    binding.btnOpenDocumentTree.setOnClickListener((v) -> openDocumentTree());
    binding.btnPickContact.setOnClickListener((v) -> pickContact());
    binding.btnInputText.setOnClickListener((v) -> inputText());
  }

  private void requestPermission() {
    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION,
        () -> toast(R.string.location_permission_granted),
        (settingsLauncher) -> showDialog(R.string.need_permission_title, R.string.no_location_permission, settingsLauncher::launch),
        () -> showDialog(R.string.need_permission_title, R.string.need_location_permission, this::requestPermission)
    );
  }

  private void requestMultiplePermissions() {
    requestMultiplePermissionsLauncher.launch(
        new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
        },
        () -> toast(R.string.location_and_read_permissions_granted),
        (list, settingsLauncher) -> {
          int message;
          if (list.size() == 2) {
            message = R.string.need_location_and_read_permissions;
          } else if (list.get(0).equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            message = R.string.need_location_permission;
          } else {
            message = R.string.need_read_permission;
          }
          showDialog(R.string.need_permission_title, message, settingsLauncher::launch);
        },
        (list) -> {
          int message;
          if (list.size() == 2) {
            message = R.string.need_location_and_read_permissions;
          } else if (list.get(0).equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            message = R.string.need_location_permission;
          } else {
            message = R.string.need_read_permission;
          }
          showDialog(R.string.need_permission_title, message, this::requestMultiplePermissions);
        }
    );
  }

  private void goToAppDetailsSettings() {
    appDetailsSettingsLauncher.launch();
  }

  private void takePicturePreview() {
    takePicturePreviewLauncher.launch((bitmap) -> {
      if (bitmap != null) {
        new PictureDialogFragment(bitmap).show(getSupportFragmentManager());
      }
    });
  }

  private void takePicture() {
    takePictureLauncher.launch((uri, file) -> {
      if (uri != null && file != null) {
        new PictureDialogFragment(uri, file).show(getSupportFragmentManager());
      }
    });
  }

  private void takePictureAndCropIt() {
    takePictureLauncher.launch((uri, file) -> {
      if (uri != null && file != null) {
        cropPictureLauncher.launch(uri, (croppedUri, croppedFile) -> {
          //noinspection ResultOfMethodCallIgnored
          file.delete();
          if (croppedUri != null && croppedFile != null) {
            new PictureDialogFragment(croppedUri, croppedFile).show(getSupportFragmentManager());
          }
        });
      }
    });
  }

  private void takeVideo() {
    takeVideoLauncher.launch((uri, file) -> {
      if (uri != null && file != null) {
        new PictureDialogFragment(uri, file).show(getSupportFragmentManager());
      }
    });
  }

  private void pickPicture() {
    pickContentLauncher.launchForImage(
        (uri, file) -> {
          if (uri != null && file != null) {
            new PictureDialogFragment(uri, file).show(getSupportFragmentManager());
          }
        },
        (settingsLauncher) -> showDialog(R.string.need_permission_title, R.string.no_read_permission, settingsLauncher::launch),
        () -> showDialog(R.string.need_permission_title, R.string.need_read_permission, this::pickPicture)
    );
  }

  private void pickVideo() {
    pickContentLauncher.launchForVideo(
        (uri, file) -> {
          if (uri != null && file != null) {
            new PictureDialogFragment(uri, file).show(getSupportFragmentManager());
          }
        },
        (settingsLauncher) -> showDialog(R.string.need_permission_title, R.string.no_read_permission, settingsLauncher::launch),
        () -> showDialog(R.string.need_permission_title, R.string.need_read_permission, this::pickPicture)
    );
  }

  private void getMultiplePicture() {
    getMultipleContentsLauncher.launchForImage(
        (uris, files) -> {
          if (files.size() > 0) {
            List<String> filenames = new ArrayList<>();
            for (File file : files) {
              filenames.add(file.getName());
            }
            showItems(R.string.selected_files, filenames);
          }
        },
        (settingsLauncher) -> showDialog(R.string.need_permission_title, R.string.no_read_permission, settingsLauncher::launch),
        () -> showDialog(R.string.need_permission_title, R.string.need_read_permission, this::pickPicture)
    );
  }

  private void getMultipleVideo() {
    getMultipleContentsLauncher.launchForVideo(
        (uris, files) -> {
          if (files != null) {
            List<String> filenames = new ArrayList<>();
            for (File file : files) {
              filenames.add(file.getName());
            }
            showItems(R.string.selected_files, filenames);
          }
        },
        (settingsLauncher) -> showDialog(R.string.need_permission_title, R.string.no_read_permission, settingsLauncher::launch),
        () -> showDialog(R.string.need_permission_title, R.string.need_read_permission, this::pickPicture)
    );
  }

  private void enableBluetooth() {
    enableBluetoothLauncher.launchAndEnableLocation(
        R.string.enable_location_reason,
        (enabled) -> {
          if (enabled) {
            toast(R.string.bluetooth_enabled);
          }
        },
        (settingsLauncher) -> showDialog(R.string.need_permission_title, R.string.bluetooth_need_location_permission, settingsLauncher::launch),
        () -> showDialog(R.string.need_permission_title, R.string.need_location_permission, this::enableBluetooth)
    );
  }

  private void enableLocation() {
    enableLocationLauncher.launch((enabled) -> {
      if (enabled) {
        toast(R.string.location_enabled);
      } else {
        toast(R.string.location_disabled);
      }
    });
  }

  private void createDocument() {
    createDocumentLauncher.launch((uri) -> {
      if (uri != null) {
        Cursor cursor = getContentResolver().query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
        if (cursor.moveToFirst()) {
          String name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
          toast(name);
        }
        cursor.close();
      }
    });
  }

  private void openDocument() {
    openDocumentLauncher.launch(new String[]{"application/*"}, (uri) -> {
      if (uri != null) {
        Cursor cursor = getContentResolver().query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
        if (cursor.moveToFirst()) {
          String name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
          toast(name);
        }
        cursor.close();
      }
    });
  }

  private void openMultipleDocuments() {
    openMultipleDocumentsLauncher.launch(new String[]{"application/*"}, (uris) -> {
      if (uris.size() > 0) {
        List<String> filenames = new ArrayList<>();
        for (Uri uri : uris) {
          Cursor cursor = getContentResolver().query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
          if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
            filenames.add(name);
          }
          cursor.close();
        }
        showItems(R.string.documents, filenames);
      }
    });
  }

  private void openDocumentTree() {
    openDocumentTreeLauncher.launch((uri) -> {
      if (uri != null) {
        DocumentFile documentFile = DocumentFile.fromTreeUri(this, uri);
        List<String> filenames = new ArrayList<>();
        if (documentFile != null) {
          for (DocumentFile file : documentFile.listFiles()) {
            filenames.add(file.getName());
          }
        }
        showItems(R.string.documents, filenames);
      }
    });
  }

  private void pickContact() {
    pickContactLauncher.launch((uri) -> {
      if (uri != null) {
        Cursor cursor = getContentResolver().query(uri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        if (cursor.moveToFirst()) {
          String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME));
          toast(name);
        }
        cursor.close();
      }
    });
  }

  private void inputText() {
    inputTextLauncher.launch("name", "Input name", (value) -> {
      if (value != null) {
        toast(value);
      }
    });
  }
}