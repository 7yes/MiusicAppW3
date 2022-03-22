package com.sevenyes.miapi.ui.views

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenyes.miapi.adapter.ClassicAdapter
import com.sevenyes.miapi.databinding.FragmentClassicBinding
import com.sevenyes.miapi.model.ClassicList
import com.sevenyes.miapi.presenter.MusicGenreViewContract
import com.sevenyes.miapi.presenter.ClassicPresenter


class ClassicFragment : Fragment(), MusicGenreViewContract<ClassicList> {

    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }
    private val classicAdapter by lazy {
        ClassicAdapter(onClassicTrackClick = {
            Intent(android.content.Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(it.previewUrl), "audio/*")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(this)
            }
        })
    }

    private val presenter by lazy {
        ClassicPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.rvClassic.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = classicAdapter
        }

        binding.RefreshClassicFragment.setOnRefreshListener {
            presenter.reload()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        presenter.reload()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onSuccess(trackList: ClassicList) {
        classicAdapter.updateClassic(trackList.tracks)
        binding.RefreshClassicFragment.isRefreshing=false
    }

    override fun onError(throwable: Throwable) {
        AlertDialog
            .Builder(requireContext())
            .setTitle("ERROR!!")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("OK"
            ) { _, _ ->  }.show()
    }


}