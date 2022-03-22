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
import com.sevenyes.miapi.adapter.PopAdapter
import com.sevenyes.miapi.databinding.FragmentPopBinding
import com.sevenyes.miapi.model.PopList
import com.sevenyes.miapi.network.RetroFItService
import com.sevenyes.miapi.presenter.MusicGenreViewContract
import com.sevenyes.miapi.presenter.PopPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PopFragment : Fragment(), MusicGenreViewContract<PopList> {


    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    private val presenter by lazy {
        PopPresenter(this)
    }

    private val popAdapter by lazy {
        PopAdapter(onPopClick = {
            Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(it.previewUrl), "audio/*")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(this)
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.rvPop.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = popAdapter
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
       presenter.getPop()
    }

    override fun onSuccess(trackList: PopList) {
        popAdapter.updatePop(trackList.tracks)
    }

    override fun onError(throwable: Throwable) {
        AlertDialog
            .Builder(requireContext())
            .setTitle("ERROR!!")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("OK"
            ) { _, _ ->  }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }
}