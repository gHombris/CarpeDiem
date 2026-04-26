package com.example.digitalwellbeing;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class ShortsBlockerService extends AccessibilityService {

    private static final String TAG = "ShortsBlockerService";
    
    private static final String TIKTOK_APP = "com.zhiliaoapp.musically";
    private static final String YOUTUBE_APP = "com.google.android.youtube";
    private static final String INSTAGRAM_APP = "com.instagram.android";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null || event.getPackageName() == null) {
            return;
        }

        SharedPreferences prefs = getSharedPreferences("carpediem_prefs", MODE_PRIVATE);
        boolean isBlockingEnabled = prefs.getBoolean("is_blocking_enabled", true);
        
        if (!isBlockingEnabled) {
            return;
        }

        boolean blockTiktok = prefs.getBoolean("block_tiktok", true);
        boolean blockYoutube = prefs.getBoolean("block_youtube", true);
        boolean blockInstagram = prefs.getBoolean("block_instagram", true);

        String packageName = event.getPackageName().toString();

        if (blockTiktok && TIKTOK_APP.equals(packageName)) {
            Log.d(TAG, "TikTok detectado. Bloqueando...");
            launchBlockScreen();
            return;
        }

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            if (blockYoutube && YOUTUBE_APP.equals(packageName) && isWatchingShorts(rootNode)) {
                Log.d(TAG, "YouTube Shorts detectado. Bloqueando...");
                launchBlockScreen();
            } else if (blockInstagram && INSTAGRAM_APP.equals(packageName) && isWatchingReels(rootNode)) {
                Log.d(TAG, "Instagram Reels detectado. Bloqueando...");
                launchBlockScreen();
            }
            rootNode.recycle();
        }
    }

    private boolean isWatchingShorts(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) return false;

        String desc = nodeInfo.getContentDescription() != null ? nodeInfo.getContentDescription().toString().toLowerCase() : "";
        String text = nodeInfo.getText() != null ? nodeInfo.getText().toString().toLowerCase() : "";

        if (text.contains("shorts") || desc.contains("shorts")) {
            boolean isTab = (text.equals("shorts") || desc.equals("shorts") || desc.contains("aba") || desc.contains("tab"));
            if (isTab) {
                if (nodeInfo.isSelected()) {
                    return true;
                }
            } else {
                return true;
            }
        }

        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo child = nodeInfo.getChild(i);
            if (child != null) {
                boolean found = isWatchingShorts(child);
                child.recycle();
                if (found) return true;
            }
        }
        return false;
    }

    private boolean isWatchingReels(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) return false;

        String desc = nodeInfo.getContentDescription() != null ? nodeInfo.getContentDescription().toString().toLowerCase() : "";
        String text = nodeInfo.getText() != null ? nodeInfo.getText().toString().toLowerCase() : "";

        if (text.contains("reels") || desc.contains("reels")) {
            boolean isTab = (text.equals("reels") || desc.equals("reels") || desc.contains("aba") || desc.contains("tab"));
            if (isTab) {
                if (nodeInfo.isSelected()) {
                    return true;
                }
            } else {
                return true;
            }
        }

        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo child = nodeInfo.getChild(i);
            if (child != null) {
                boolean found = isWatchingReels(child);
                child.recycle();
                if (found) return true;
            }
        }
        return false;
    }

    private void launchBlockScreen() {
        Intent intent = new Intent(this, BlockOverlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onInterrupt() {
    }
}
