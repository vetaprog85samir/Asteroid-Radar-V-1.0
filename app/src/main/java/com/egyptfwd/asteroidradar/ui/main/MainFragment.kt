package com.egyptfwd.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.egyptfwd.asteroidradar.R
import com.egyptfwd.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MainViewModel.Factory(activity.application))[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = MainRVAdapter(MainRVAdapter.AsteroidListener{
            asteroid -> viewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer { asteroid ->
            if(null != asteroid){
                this.findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(asteroid)
                )
                viewModel.onAsteroidNavigated()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.week_asteroids -> viewModel.onWeekAsteroidsClicked()
            R.id.today_asteroids -> viewModel.onTodayAsteroidsClicked()
            R.id.save_asteroids -> viewModel.onSavedAsteroidsClicked()
        }
        return true
    }
}


