package com.sevenyes.miapi.ui.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sevenyes.miapi.adapter.RockAdapter
import com.sevenyes.miapi.databinding.FragmentRockBinding
import com.sevenyes.miapi.model.Rock
import com.sevenyes.miapi.model.RockList
import com.sevenyes.miapi.network.RetroFItService
import com.sevenyes.miapi.presenter.MusicGenreViewContract
import com.sevenyes.miapi.presenter.RockPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RockFragment : Fragment(), MusicGenreViewContract<List<Rock>>{

    private var _binding: FragmentRockBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val rockAdapter =  RockAdapter(onRockClick = {
        if (presenter.isConnected())
            Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(it.previewUrl), "audio/*")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(this)
            }
        else Toast.makeText(requireContext(),"No Internet Connection ", Toast.LENGTH_SHORT).show()
    })

    private val presenter by lazy{
        RockPresenter(requireContext(),this )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRockBinding.inflate(inflater, container, false)
        val root: View = binding.root

        presenter.checkNetwork();

        binding.rvRock.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false )
            adapter = rockAdapter
        }
        return root
    }

    override fun onResume() {
        super.onResume()
      presenter.getRock()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    presenter.destroy()
        _binding = null
    }

    override fun onSuccess(trackList: List<Rock>) {
         rockAdapter.updateRock(trackList)
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