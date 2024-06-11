package com.example.composetutorial.presentation.feature.tips_10

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.datastore.preferences.protobuf.Internal.toByteArray
import com.example.composetutorial.R
import java.io.ByteArrayOutputStream

@Composable
fun Tips10Screen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img)
    val encryptedBytes = bitmap.toByteArray()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            BadEncryptedImage(encryptedBytes)
            GoodEncryptedImage(encryptedBytes)
        }
    }
}


@Composable
fun BadEncryptedImage(
    encryptedBytes: ByteArray, modifier: Modifier = Modifier
) {
    val decryptedBytes = CryptoManager.decrypt(encryptedBytes)
    val bitmap = BitmapFactory.decodeByteArray(decryptedBytes, 0, decryptedBytes.size)
    val count by remember { mutableIntStateOf(0) }

    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = modifier)

    Button(onClick = {
        count.inc()
    }) {
        Text("Refresh")
    }
}

@Composable
fun GoodEncryptedImage(
    encryptedBytes: ByteArray, modifier: Modifier = Modifier
) {
    val bitmap = remember(encryptedBytes) {
        val decryptedBytes = CryptoManager.decrypt(encryptedBytes)
        BitmapFactory.decodeByteArray(decryptedBytes, 0, decryptedBytes.size)
    }
    val count by remember { mutableIntStateOf(0) }

    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = modifier)

    Button(onClick = {
        count.inc()
    }) {
        Text("Refresh")
    }
}

private fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

object CryptoManager {
    fun decrypt(encryptedBytes: ByteArray): ByteArray {
        return encryptedBytes
    }
}