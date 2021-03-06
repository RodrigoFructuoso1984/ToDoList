package com.rfructuoso.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rfructuoso.todolist.R
import com.rfructuoso.todolist.databinding.ItemTaskBinding
import com.rfructuoso.todolist.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    inner class TaskViewHolder(
        private  val binding: ItemTaskBinding
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.textViewTitle.text = item.title
            binding.textViewDate.text = "${item.date} ${item.hour}"
            binding.imageViewMore.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val imageViewMore = binding.imageViewMore
            val popupMenu = PopupMenu(imageViewMore.context, imageViewMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> listenerEdit(item)
                R.id.action_delete -> listenerDelete(item)
            }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}