package com.geektech.intellect_memort.presentation.ui.fragments.picture.game.answering

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseFragment
import com.geektech.intellect_memort.databinding.FragmentPicturePlayGameAnsweringBinding
import com.geektech.intellect_memort.domain.models.PictureImageModel
import com.geektech.intellect_memort.domain.models.PictureQuestionModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicturePlayGameAnswering : BaseFragment<FragmentPicturePlayGameAnsweringBinding,
        PicturePlayGameAnsweringViewModel>(R.layout.fragment_picture_play_game_answering),
    OnClickListener {

    override val binding by viewBinding(FragmentPicturePlayGameAnsweringBinding::bind)
    override val viewModel: PicturePlayGameAnsweringViewModel by viewModels()
    private val args: PicturePlayGameAnsweringArgs by navArgs()

    // game variables
    private val newListOfImages = mutableListOf<PictureImageModel>()
    private val listOfQuestions = mutableListOf<PictureQuestionModel>()
    private val shownImagesNumber = 9
    private var currentImagePosition = 0
    private var mSelectedOptionId: String? = ""
    private var sizeOfShownImages: Int = 0
    private var correctAnswers: Int = 0


    override fun initialize() {
        super.initialize()
        navigationToMenu()
        gameInitialization()

    }

    override fun setupListeners() {
        super.setupListeners()
        sizeOfShownImages = listOfQuestions.size - 1

        binding.btnFirst.setOnClickListener(this)
        binding.btnSecond.setOnClickListener(this)
        binding.btnThird.setOnClickListener(this)
        binding.btnFourth.setOnClickListener(this)

        binding.btnNext.setOnClickListener {
            if (currentImagePosition < sizeOfShownImages) {
                checkCorrectAnswer()
                currentImagePosition++
                imageQuestionSetup()
            }
        }
        binding.btnPrevious.setOnClickListener {
            if (currentImagePosition > 0) {
                currentImagePosition--
                imageQuestionSetup()
            }
        }
        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(PicturePlayGameAnsweringDirections
                .actionPicturePlayGameAnsweringToPictureGameResults(
                    listOfQuestions.size,
                    correctAnswers,
                    listOfQuestions.size - correctAnswers,
                    args.passedTime
                ))
        }
    }

    // TextViews onClick realization
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_first -> {
                selectedOptionView(binding.btnFirst,
                    listOfQuestions[currentImagePosition].firstAnswer)
            }
            R.id.btn_second -> {
                selectedOptionView(binding.btnSecond,
                    listOfQuestions[currentImagePosition].secondAnswer)
            }
            R.id.btn_third -> {
                selectedOptionView(binding.btnThird,
                    listOfQuestions[currentImagePosition].thirdAnswer)
            }
            R.id.btn_fourth -> {
                selectedOptionView(binding.btnFourth,
                    listOfQuestions[currentImagePosition].fourthAnswer)
            }
        }
    }

    // populating views with images
    private fun gameInitialization() {
        // images passed from PicturePlayGame fragment
        val allPassedImagesWithinRange = args.imagesList.images.shuffled()
        var allPassedImagesWithinRangeRandomized = args.imagesList.images
        correctAnswers = 0


        for (imageModel in 0..shownImagesNumber) {
            newListOfImages.add(allPassedImagesWithinRange[imageModel])
        }



        for (i in 0..shownImagesNumber) {
            allPassedImagesWithinRangeRandomized = allPassedImagesWithinRangeRandomized.shuffled()
            listOfQuestions.add(
                PictureQuestionModel(
                    imageModel = newListOfImages[i],
                    correctAnswer = newListOfImages[i].id,
                    firstAnswer = newListOfImages[i].id,
                    secondAnswer = allPassedImagesWithinRangeRandomized[1].id,
                    thirdAnswer = allPassedImagesWithinRangeRandomized[2].id,
                    fourthAnswer = allPassedImagesWithinRangeRandomized[3].id
                )
            )
        }


        imageQuestionSetup()

    }

    // setup for images by position(every click)
    private fun imageQuestionSetup() {
        val currentImageModel = listOfQuestions[currentImagePosition]
        defaultOptionsView()

        if (currentImagePosition == listOfQuestions.size - 1) {
            binding.btnNext.visibility = GONE
            binding.btnPrevious.visibility = GONE
            binding.btnSubmit.visibility = VISIBLE
        }


        Glide.with(binding.image)
            .load(currentImageModel.imageModel.imageUrl)
            .into(binding.image)

        var randomizedListOfAnswers = mutableListOf<String?>()
        randomizedListOfAnswers.add(currentImageModel.firstAnswer)
        randomizedListOfAnswers.add(currentImageModel.secondAnswer)
        randomizedListOfAnswers.add(currentImageModel.thirdAnswer)
        randomizedListOfAnswers.add(currentImageModel.fourthAnswer)

        randomizedListOfAnswers = randomizedListOfAnswers.shuffled() as MutableList<String?>

        binding.btnFirst.text = randomizedListOfAnswers[0]
        binding.btnSecond.text = randomizedListOfAnswers[1]
        binding.btnThird.text = randomizedListOfAnswers[2]
        binding.btnFourth.text = randomizedListOfAnswers[3]

    }

    // textViews default state
    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.btnFirst)
        options.add(1, binding.btnSecond)
        options.add(2, binding.btnThird)
        options.add(3, binding.btnFourth)

        options.forEach {
            it.setBackgroundResource(R.drawable.black_border_rectangle)
        }
    }

    // textViews selected State
    private fun selectedOptionView(
        view: TextView,
        selectedOptionId: String?,
    ) {
        defaultOptionsView()
        mSelectedOptionId = selectedOptionId
        view.setBackgroundResource(R.color.yellow)
    }

    // correct answer check
    private fun checkCorrectAnswer() {
        val imageModel = listOfQuestions[currentImagePosition]
        if (mSelectedOptionId.equals(imageModel.correctAnswer)) {
            correctAnswers++
        }
    }

    // alert dialog - leave to menu
    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext()).setMessage("Выйти в главное меню?")
            .setPositiveButton("Да") { _, _ ->
                Log.e("correct ", correctAnswers.toString(), )
                findNavController().navigate(PicturePlayGameAnsweringDirections
                    .actionPicturePlayGameAnsweringToHomeFragment())
            }
            .setNegativeButton("Нет") { _, _ -> }
            .show()
    }

    // navigation to home fragment
    private fun navigationToMenu() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlertDialog()
                }
            })
    }

}


