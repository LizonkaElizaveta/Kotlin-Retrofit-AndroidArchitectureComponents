package stanevich.elizaveta.hoteldisplay.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import stanevich.elizaveta.hoteldisplay.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application

        val binding = FragmentDescriptionBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val hotelsProperty = DescriptionFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = DescriptionViewModelFactory(hotelsProperty, application)
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(DescriptionViewModel::class.java)
        return binding.root
    }
}