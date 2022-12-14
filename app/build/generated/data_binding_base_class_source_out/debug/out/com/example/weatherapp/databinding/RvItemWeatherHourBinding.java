// Generated by view binder compiler. Do not edit!
package com.example.weatherapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.weatherapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RvItemWeatherHourBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout rvItemWeatherHour;

  @NonNull
  public final ImageView rvIvWeather;

  @NonNull
  public final TextView rvTvDegree;

  @NonNull
  public final TextView rvTvTime;

  private RvItemWeatherHourBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout rvItemWeatherHour, @NonNull ImageView rvIvWeather,
      @NonNull TextView rvTvDegree, @NonNull TextView rvTvTime) {
    this.rootView = rootView;
    this.rvItemWeatherHour = rvItemWeatherHour;
    this.rvIvWeather = rvIvWeather;
    this.rvTvDegree = rvTvDegree;
    this.rvTvTime = rvTvTime;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RvItemWeatherHourBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RvItemWeatherHourBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.rv_item_weather_hour, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RvItemWeatherHourBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout rvItemWeatherHour = (ConstraintLayout) rootView;

      id = R.id.rv_iv_weather;
      ImageView rvIvWeather = ViewBindings.findChildViewById(rootView, id);
      if (rvIvWeather == null) {
        break missingId;
      }

      id = R.id.rv_tv_degree;
      TextView rvTvDegree = ViewBindings.findChildViewById(rootView, id);
      if (rvTvDegree == null) {
        break missingId;
      }

      id = R.id.rv_tv_time;
      TextView rvTvTime = ViewBindings.findChildViewById(rootView, id);
      if (rvTvTime == null) {
        break missingId;
      }

      return new RvItemWeatherHourBinding((ConstraintLayout) rootView, rvItemWeatherHour,
          rvIvWeather, rvTvDegree, rvTvTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
