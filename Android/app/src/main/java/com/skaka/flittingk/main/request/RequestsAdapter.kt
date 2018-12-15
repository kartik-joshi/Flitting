package com.skaka.flittingk.main.request

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skaka.flittingk.R
import com.skaka.flittingk.model.RequestDTO
import com.skaka.flittingk.util.BaseURLImage
import com.skaka.flittingk.util.clearAndAddAll
import kotlinx.android.synthetic.main.row_request.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class RequestsAdapter(val requests: List<RequestDTO>) : RecyclerView.Adapter<RequestsViewHolder>() {
    private val results = ArrayList<RequestDTO>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RequestsViewHolder =
        RequestsViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.row_request, p0, false))

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(vh: RequestsViewHolder, pos: Int) {
        vh.bind(results[pos], this@RequestsAdapter)
    }

    fun filter(q: String) {
        Log.d("RequestsAdapter", "filter: ")
        if (!q.isBlank()) {
            results.clear()
            results.addAll(requests.stream()
                .filter { it.userProfile.getName().contains(q, true) || it.description.contains(q, true) }
                .collect(Collectors.toList()))
        } else {
            Log.d("RequestsAdapter", "filter: else")
            results.clearAndAddAll(requests)
        }

        notifyDataSetChanged()
    }
}

class RequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(r: RequestDTO, requestsAdapter: RequestsAdapter) = with(itemView) {
        if (r.isAccepted) {
            requestAcceptButton.isEnabled = false
            requestRejectButton.visibility = View.INVISIBLE
            requestAcceptButton.text = "Accepted"
        } else {
            requestAcceptButton.setOnClickListener {
                r.isAccepted = true
                requestsAdapter.notifyDataSetChanged()
            }
        }

        requestText.text = r.description
        requestSender.text = r.userProfile.getName()
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val start = df.parse(r.startDate)
        val df2 = SimpleDateFormat("MM-dd", Locale.US)
        requestDate.text = df2.format(start)
        Glide.with(context)
            .load(BaseURLImage + r.userProfile.image)
            .apply(RequestOptions.centerCropTransform().placeholder(R.drawable.bird_white))
            .into(requestImage)
    }
}

