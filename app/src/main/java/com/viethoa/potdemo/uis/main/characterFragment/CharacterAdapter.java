package com.viethoa.potdemo.uis.main.characterFragment;

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
import com.viethoa.potdemo.models.Character;
import com.viethoa.potdemo.models.Movie;
import com.viethoa.potdemo.models.Thumbnail;
import com.viethoa.potdemo.uis.characterdetails.CharacterDetailsActivity;
import com.viethoa.potdemo.uis.moviedetails.MovieDetailsActivity;
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
public class CharacterAdapter extends BaseArrayAdapter<Character> {

    public CharacterAdapter(Context context, List<Character> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_character, viewGroup, false);
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
        @Bind(R.id.img_thumbnail)
        ImageView imgThumbnail;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_description)
        TextView tvDescription;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Context context, Character character) {
            if (character == null) {
                return;
            }

            // Name
            String name = character.getName();
            if (!TextUtils.isEmpty(name)) {
                tvName.setText(name);
            }

            // Description
            String description = character.getDescription();
            if (!TextUtils.isEmpty(description)) {
                tvDescription.setText(description);
            } else {
                tvDescription.setText(R.string.na);
            }

            // Thumbnail
            Thumbnail thumbnail = character.getThumbnail();
            if (thumbnail != null) {
                GlideUtils.loadImage(context, thumbnail.getThumbnailUrl(), imgThumbnail,
                        R.drawable.placeholder_image);
            }

            // Navigate to detail screen
            vContainer.setOnClickListener(view -> {
                navigationToDetailScreen(context, character);
            });
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void navigationToDetailScreen(Context context, Character character) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(CharacterDetailsActivity.newInstance(context, character));
                return;
            }

            ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                    new Pair(tvName, tvName.getTransitionName()),
                    new Pair(tvDescription, tvDescription.getTransitionName()),
                    new Pair(imgThumbnail, imgThumbnail.getTransitionName()));

            Observable.just(CharacterDetailsActivity.newInstance(context, character))
                    .delay(200, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(intent -> {
                        context.startActivity(intent, compat.toBundle());
                    });
        }
    }
}
