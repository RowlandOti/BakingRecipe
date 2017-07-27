package com.rowland.bakingapp.ui.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rowland.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    // The class Log identifier
    private static final String LOG_TAG = VideoFragment.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;
    private String videoUrl;
    private Uri uri;

    @BindView(R.id.video_player)
    SimpleExoPlayerView mExoplayerView;
    @BindView(R.id.video_player_close)
    ImageView mExoplayerCloseView;


    interface VideoCallBack {
        void hideVideoFragment();
    }

    public void setVideoCallBack(VideoCallBack mVideoCallBack) {
        this.mVideoCallBack = mVideoCallBack;
    }

    private VideoCallBack mVideoCallBack;

    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance(Bundle args) {
        VideoFragment frag = new VideoFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Bundle args = getArguments();
        videoUrl = args.getString(StepFragment.SELECTED_VIDEO_KEY);
        cfgExoPlayer();

        mExoplayerCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoCallBack.hideVideoFragment();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExoPlayer != null) {
            uri = Uri.parse(videoUrl);
            cfgVideoSource();
            mExoplayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void cfgExoPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
    }

    private void cfgVideoSource() {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getContext().getString(R.string.app_name)));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
        mExoPlayer.prepare(videoSource);
    }

}
