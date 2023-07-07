package com.example.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(navCommand: NavCommand) {
    findNavController().navigate(navCommand.action, navCommand.args, navCommand.navOptions)
}

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}