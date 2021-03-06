/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Copyright (C) 2016 Julian Andres Klode <jak@jak-linux.org>
 * Copyright (C) 2016 avalond <agonyice0115@gmail.com>
 */
package com.avalond.ad_blocak.main;

import com.avalond.ad_blocak.FileHelper;
import com.avalond.ad_blocak.MainActivity;
import java.util.Collections;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Simple ItemTouchHelper callback for a collection based adapter.
 * @author kevin
 */
class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
  private final ItemRecyclerViewAdapter mAdapter;


  public ItemTouchHelperCallback(ItemRecyclerViewAdapter mAdapter) {
    super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    this.mAdapter = mAdapter;
  }


  @Override
  public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    int a = viewHolder.getAdapterPosition();
    int b = target.getAdapterPosition();

    Collections.swap(mAdapter.items, a, b);
    mAdapter.notifyItemMoved(a, b);

    return true;
  }


  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
    mAdapter.items.remove(viewHolder.getAdapterPosition());
    mAdapter.notifyDataSetChanged();
  }


  public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    super.clearView(recyclerView, viewHolder);
    Log.d("ItemTouchHelperCallback", "clearView: Done with interaction. Saving settings.");
    FileHelper.writeSettings(viewHolder.itemView.getContext(), MainActivity.config);
  }
}
