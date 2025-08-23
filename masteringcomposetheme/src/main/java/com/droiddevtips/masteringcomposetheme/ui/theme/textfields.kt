package com.droiddevtips.masteringcomposetheme.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * These are the [TextField] examples
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun TextFieldExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (isFocused.value) {
                IconButton(onClick = {
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            }
        },
        supportingText = {
            Text("Your app user name")
        },
        placeholder = {
            Text("Enter username or email")
        },
        label = {
            Text("Username")
        },
        value = username.value,
        onValueChange = { value ->
            username.value = value
        },
        singleLine = true
    )
}

@Composable
fun TextFieldCustomLeadingIconColorExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Text field with custom leading icon color")

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedPrefixColor = Color.Red
            ),
            trailingIcon = {
                if (isFocused.value) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            supportingText = {
                Text("Your app user name")
            },
            label = {
                Text("Username")
            },
            value = username.value,
            onValueChange = { value ->
                username.value = value
            },
            singleLine = true
        )
    }
}

@Composable
fun TextFieldErrorStateExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Text field in error state")

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            },
            supportingText = {
                Text("Your app user name")
            },
            placeholder = {
                Text("Enter username or email")
            },
            label = {
                Text("Username")
            },
            value = "Invalid username",
            isError = true,
            onValueChange = { },
            singleLine = true
        )
    }
}

@Composable
fun TextFieldInDisableStateExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Text field in disabled state")

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            trailingIcon = {
                IconButton(onClick = {
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            },
            supportingText = {
                Text("Your app user name")
            },
            placeholder = {
                Text("Enter username or email")
            },
            label = {
                Text("Username")
            },
            value = username.value,
            onValueChange = { value ->
                username.value = value
            },
            singleLine = true
        )
    }
}

@Composable
fun TextFieldWithSuffixExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Text field with suffix")

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (isFocused.value) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            supportingText = {
                Text("Your email account")
            },
            suffix = {
                Text("@example.com")
            },
            label = {
                Text("Email account")
            },
            value = username.value,
            onValueChange = { value ->
                username.value = value
            },
            singleLine = true
        )
    }
}

@Composable
fun TextFieldWithPrefixExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Text field with prefix")

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                focusedPrefixColor = Color.Red
            ),
            trailingIcon = {
                if (isFocused.value) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            supportingText = {
                Text("Your email domain")
            },
            prefix = {
                Text("info@")
            },
            label = {
                Text("Email domain")
            },
            value = username.value,
            onValueChange = { value ->
                username.value = value
            },
            singleLine = true
        )
    }
}

@Composable
fun PasswordTextFieldExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Password text field")

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = null
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                if (isFocused.value) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            placeholder = {
                Text("Enter your password")
            },
            supportingText = {
                Text("Account password")
            },
            label = {
                Text("Password")
            },
            value = username.value,
            onValueChange = { value ->
                username.value = value
            },
            singleLine = true
        )
    }
}

@Composable
fun OutlineTextFieldExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Outlined Text field")

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = null
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                if (isFocused.value) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            },
            supportingText = {
                Text("Account password")
            },
            label = {
                Text("Password")
            },
            placeholder = {
                Text("Enter your password")
            },
            value = username.value,
            onValueChange = { value ->
                username.value = value
            },
            singleLine = true
        )
    }
}

@Composable
fun OutlineTextFieldErrorExample(modifier: Modifier = Modifier) {

    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("") }
    val isFocused = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Outlined Text field (Error)")

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = null
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
            },
            supportingText = {
                Text("Account password")
            },
            label = {
                Text("Password")
            },
            placeholder = {
                Text("Enter your password")
            },
            value = username.value,
            onValueChange = { value ->
                username.value = value
            },
            isError = true,
            singleLine = true
        )
    }
}