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

    // size of image quiz
    private val shownImagesNumber = 9 // starts from 0
    private var sizeOfShownImages: Int = 0

    // position vars
    private var currentImagePosition = 0
    private var mSelectedOptionId: String? = ""

    // list vars
    private val listOfQuestions = mutableListOf<PictureQuestionModel>()
    private var listOfAnswersForViewRandomized = mutableListOf<String?>()
    private var listOfAnswers = mutableListOf<String?>()

    // correct answers to submit
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
                listOfAnswers.add(mSelectedOptionId)
                currentImagePosition++
                imageQuestionSetup()
            }
        }
        binding.btnPrevious.setOnClickListener {
            if (currentImagePosition > 0) {
                listOfAnswers.removeLast()
                currentImagePosition--
                imageQuestionSetup()
            }
        }
        binding.btnSubmit.setOnClickListener {
            checkCorrectAnswer()
            findNavController().navigate(PicturePlayGameAnsweringDirections
                .actionPicturePlayGameAnsweringToPictureGameResults(
                    totalPictures = listOfQuestions.size,
                    correctAnswers = correctAnswers,
                    incorrectAnswers = listOfQuestions.size - correctAnswers,
                    totalTime = args.passedTime
                ))
        }
    }

    // TextViews onClick realization
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_first -> {
                selectedOptionView(binding.btnFirst,
                    listOfAnswersForViewRandomized[0]
                )
            }
            R.id.btn_second -> {
                selectedOptionView(binding.btnSecond,
                    listOfAnswersForViewRandomized[1])
            }
            R.id.btn_third -> {
                selectedOptionView(binding.btnThird,
                    listOfAnswersForViewRandomized[2]
                )
            }
            R.id.btn_fourth -> {
                selectedOptionView(binding.btnFourth,
                    listOfAnswersForViewRandomized[3])
            }
        }
    }

    // populating views with images
    private fun gameInitialization() {
        // images passed from PicturePlayGame fragment
        val allImages = args.imagesList.images.shuffled()
        var allImagesRandomized = args.imagesList.images
        correctAnswers = 0


        for (i in 0..shownImagesNumber) {
            while (allImagesRandomized[1].id == allImages[i].id ||
                allImagesRandomized[2].id == allImages[i].id ||
                allImagesRandomized[3].id == allImages[i].id
            ) {
                allImagesRandomized =
                    allImagesRandomized.shuffled()
            }
            listOfQuestions.add(
                PictureQuestionModel(
                    imageModel = allImages[i],
                    correctAnswer = allImages[i].id,
                    firstAnswer = allImages[i].id,
                    secondAnswer = allImagesRandomized[1].id,
                    thirdAnswer = allImagesRandomized[2].id,
                    fourthAnswer = allImagesRandomized[3].id
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

        // list of image id's(4) for one image model in question
        listOfAnswersForViewRandomized.clear()
        listOfAnswersForViewRandomized.add(currentImageModel.firstAnswer)
        listOfAnswersForViewRandomized.add(currentImageModel.secondAnswer)
        listOfAnswersForViewRandomized.add(currentImageModel.thirdAnswer)
        listOfAnswersForViewRandomized.add(currentImageModel.fourthAnswer)

        listOfAnswersForViewRandomized = listOfAnswersForViewRandomized.shuffled() as MutableList<String?>

        binding.btnFirst.text = listOfAnswersForViewRandomized[0]
        binding.btnSecond.text = listOfAnswersForViewRandomized[1]
        binding.btnThird.text = listOfAnswersForViewRandomized[2]
        binding.btnFourth.text = listOfAnswersForViewRandomized[3]

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
        val localCorrectAnswersList = mutableListOf<String?>()
        listOfQuestions.forEach {
            localCorrectAnswersList.add(it.correctAnswer)
        }
        for (item in 0 until sizeOfShownImages) {
            if (listOfAnswers[item] == (localCorrectAnswersList[item])) {
                correctAnswers++
            }
        }
    }

    // alert dialog - leave to menu
    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext()).setMessage("Выйти в главное меню?")
            .setPositiveButton("Да") { _, _ ->
                Log.e("correct ", correctAnswers.toString())
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


