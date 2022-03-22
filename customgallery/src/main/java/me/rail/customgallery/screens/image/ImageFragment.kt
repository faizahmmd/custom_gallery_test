package me.rail.customgallery.screens.image

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import me.rail.customgallery.R
import me.rail.customgallery.databinding.ItemImageBinding

class ImageFragment : Fragment() {
    private lateinit var binding: ItemImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_URI_STRING) }?.apply {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            context?.let {
                Glide.with(it).load(Uri.parse(getString(ARG_URI_STRING)))
                    .placeholder(R.drawable.ic_image_placeholder_24).apply(requestOptions)
                    .into(binding.image)
            }
        }
    }

    companion object {
        const val ARG_URI_STRING = "uri_string"

        @JvmStatic
        fun newInstance(uriString: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URI_STRING, uriString)
                }
            }
    }
}