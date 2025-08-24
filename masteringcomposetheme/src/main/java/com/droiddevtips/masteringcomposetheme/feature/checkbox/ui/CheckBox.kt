package com.droiddevtips.masteringcomposetheme.feature.checkbox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droiddevtips.masteringcomposetheme.feature.checkbox.data.Fruit

/**
 * These are the composable checkbox examples
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun CheckBoxExample(modifier: Modifier = Modifier) {

    Card(modifier = modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.padding(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                "Checkbox",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            CheckboxItem(
                title = "Unchecked",
                checked = false
            )

            CheckboxItem(
                title = "Checked",
                checked = true
            )

            CheckboxItem(
                title = "Unchecked",
                checked = false,
                enable = false
            )

            CheckboxItem(
                title = "Checked",
                checked = true,
                enable = false
            )

            CheckboxGroupExample()
        }
    }
}

@Composable
private fun CheckboxGroupExample(modifier: Modifier = Modifier) {

    val fruitList = remember {
        mutableStateListOf(
            Fruit("Apples", false),
            Fruit("Strawberry", false),
            Fruit("Kiwi", true)
        )
    }

    val parentState = when {
        fruitList.all { it.selected } -> ToggleableState.On
        fruitList.none { it.selected } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(modifier = modifier) {

        Text(
            "Fruit list\n(try state checkbox)",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Select all")
            TriStateCheckbox(
                state = parentState,
                enabled = false,
                onClick = {
                    val newState = parentState != ToggleableState.On
                    fruitList.forEachIndexed { index, _ ->
                        fruitList[index].selected = newState
                    }
                }
            )
        }

        fruitList.forEachIndexed { index, fruitItem ->
            CheckboxItem(
                title = fruitItem.name,
                checked = fruitItem.selected,
                enable = false,
                onCheckedChange = { isChecked ->
                    fruitList[index].selected = isChecked
                }
            )
        }
    }
}
