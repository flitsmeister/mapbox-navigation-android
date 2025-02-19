package org.maplibre.navigation.android.navigation.ui.v5.summary.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maplibre.navigation.android.navigation.ui.v5.R;
import org.maplibre.navigation.core.routeprogress.RouteProgress;
import org.maplibre.navigation.android.navigation.ui.v5.utils.DistanceFormatter;

public class InstructionListAdapter extends RecyclerView.Adapter<InstructionViewHolder> {

  private final InstructionListPresenter presenter;

  public InstructionListAdapter(DistanceFormatter distanceFormatter) {
    presenter = new InstructionListPresenter(distanceFormatter);
  }

  @NonNull
  @Override
  public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.instruction_viewholder_layout, parent, false);
    return new InstructionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
    presenter.onBindInstructionListViewAtPosition(position, holder);
  }

  @Override
  public int getItemCount() {
    return presenter.retrieveBannerInstructionListSize();
  }

  @Override
  public void onViewDetachedFromWindow(@NonNull InstructionViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    holder.itemView.clearAnimation();
  }

  public void updateBannerListWith(RouteProgress routeProgress, boolean isListShowing) {
    boolean didUpdate = presenter.updateBannerListWith(routeProgress);
    if (didUpdate && isListShowing) {
      notifyDataSetChanged();
    }
  }

  public void updateDistanceFormatter(DistanceFormatter distanceFormatter) {
    presenter.updateDistanceFormatter(distanceFormatter);
  }
}
