package com.geektech.intellect_memort.presentation.ui.fragments.picture.game.results

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.common.extension.navigateSafely
import com.geektech.intellect_memort.databinding.FragmentPictureGameResultsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureGameResults :
    BaseFragment<FragmentPictureGameResultsBinding, PictureGameResultsViewModel>
        (R.layout.fragment_picture_game_results) {

    override val binding by viewBinding(FragmentPictureGameResultsBinding::bind)
    override val viewModel: PictureGameResultsViewModel by viewModels()
    private val args: PictureGameResultsArgs by navArgs()

    override fun initialize() {
        super.initialize()
        navigationToMenu()

        binding.textviewTotalPictures.text = args.totalPictures.toString()
        binding.textviewCorrectAnswers.text = args.correctAnswers.toString()
        binding.textviewIncorrectAnswers.text = args.incorrectAnswers.toString()
        binding.textviewTotalTime.text = args.totalTime
    }

    override fun setupListeners() {
        super.setupListeners()

        binding.btnFinish.setOnClickListener {
            findNavController().navigateSafely(PictureGameResultsDirections.actionPictureGameResultsToHomeFragment())
        }
    }

    override fun setupRequests() {
        viewModel.passResults(args.correctAnswers)
    }

    private fun navigationToMenu() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateSafely(PictureGameResultsDirections.actionPictureGameResultsToHomeFragment())
                }
            })
    }

}