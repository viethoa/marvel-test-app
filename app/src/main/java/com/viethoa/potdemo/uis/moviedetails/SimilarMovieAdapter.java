package com.viethoa.potdemo.uis.moviedetails;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseArrayAdapter;
import com.viethoa.potdemo.models.Movie;
import com.viethoa.potdemo.utilities.GlideUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by VietHoa on 05/11/2016.
 */
public class SimilarMovieAdapter extends BaseArrayAdapter<Movie> {

    public SimilarMovieAdapter(Context context, List<Movie> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_similar_movie, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        ViewHolder helpViewHolder = (ViewHolder) convertView.getTag();
        helpViewHolder.bind(context, getItem(position));

        return convertView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view_container)
        View vContainer;
        @Bind(R.id.img_background)
        ImageView imgBackground;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_release_date)
        TextView tvReleaseDate;
        @Bind(R.id.tv_vote_average)
        TextView tvPopularity;
        @Bind(R.id.img_calendar)
        ImageView imgCalendarIcon;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Context context, Movie movie) {
            if (movie == null) {
                return;
            }

            // Title
            String title = movie.getTitle();
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }

            // Release date
            String releaseDate = movie.getReleaseDate();
            if (!TextUtils.isEmpty(releaseDate)) {
                tvReleaseDate.setText(releaseDate);
            }

            // IMDB
            if (movie.getVoteAverage() == 0) {
                tvPopularity.setText(context.getResources().getString(R.string.na));
            } else {
                NumberFormat formatter = NumberFormat.getNumberInstance();
                formatter.setMinimumFractionDigits(1);
                formatter.setMaximumFractionDigits(1);
                tvPopularity.setText(formatter.format(movie.getVoteAverage()));
            }

            // Background
            GlideUtils.loadImage(context, movie.getBackgroundImageUrl(),
                    GlideUtils.Size.W300, imgBackground, R.drawable.placeholder_image);

            // Navigate to detail screen
            vContainer.setOnClickListener(view -> {
                navigationToDetailScreen(context, movie);
            });
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void navigationToDetailScreen(Context context, Movie movie) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(MovieDetailsActivity.newInstance(context, movie));
                return;
            }

            ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                    new Pair(tvTitle, tvTitle.getTransitionName()),
                    new Pair(tvReleaseDate, tvReleaseDate.getTransitionName()),
                    new Pair(tvPopularity, tvPopularity.getTransitionName()),
                    new Pair(imgBackground, imgBackground.getTransitionName()),
                    new Pair(imgCalendarIcon, imgCalendarIcon.getTransitionName()));

            Observable.just(MovieDetailsActivity.newInstance(context, movie))
                    .delay(200, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(intent -> {
                        context.startActivity(intent, compat.toBundle());
                    });
        }
    }
}
