package ru.skillbranch.devintensive.ui.adapters

import android.animation.ArgbEvaluator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

class ChatItemTouchHelperCallback(
    val adapter: ChatAdapter,
    val itemIcon: Drawable? = null,
    val swipeListener: (ChatItem) -> Unit
): ItemTouchHelper.Callback() {
    private val bgRect = RectF()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val iconBounds = Rect()
    private val colorEvaluator = ArgbEvaluator()

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return if (viewHolder is ItemTouchViewHolder) {
            makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.START)
        } else {
            makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeListener.invoke(adapter.items[viewHolder.adapterPosition])
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchViewHolder) {
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemTouchViewHolder) viewHolder.onItemCleared()
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            drawBackground(canvas, itemView, dX)
            if (itemIcon != null) {
                drawIcon(canvas, itemView, dX)
            }
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawIcon(canvas: Canvas, itemView: View, dX: Float) {
        val icon = itemIcon!!
        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.icon_size)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)

        val margin = (itemView.bottom - itemView.top - iconSize) / 2
        with(iconBounds) {
            left = itemView.right + dX.toInt() + space
            top = itemView.top + margin
            right = itemView.right + dX.toInt() + iconSize + space
            bottom = itemView.bottom - margin
        }

        icon.bounds = iconBounds
        icon.draw(canvas)
    }

    private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
        with(bgRect) {
            left = itemView.left.toFloat() // dX ???
            top = itemView.top.toFloat()
            right = itemView.right.toFloat()
            bottom = itemView.bottom.toFloat()
        }

        val viewHalfWidth = itemView.width / 2
        bgPaint.color = colorEvaluator.evaluate(
            if (dX > -viewHalfWidth) 0f else -(dX + viewHalfWidth) / viewHalfWidth,
            itemView.resources.getColor(R.color.color_bg_under_item, itemView.context.theme),
            Color.WHITE
        ) as Int

        canvas.drawRect(bgRect, bgPaint)
    }
}

interface ItemTouchViewHolder {
    fun onItemSelected()
    fun onItemCleared()
}