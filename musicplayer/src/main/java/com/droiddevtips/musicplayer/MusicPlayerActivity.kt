@file:kotlin.OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.droiddevtips.musicplayer

import android.content.ComponentName
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Metadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.droiddevtips.musicplayer.mainView.MainMusicPlayerView
import com.droiddevtips.typography.DroidDevTipsTheme
import com.google.common.util.concurrent.MoreExecutors

class MusicPlayerActivity : ComponentActivity() {

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
//                val navigator = rememberListDetailPaneScaffoldNavigator<MusicTrack>()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainMusicPlayerView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val sessionToken = SessionToken(this, ComponentName(this, MusicPlayerService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()

        controllerFuture.addListener({


        }, MoreExecutors.directExecutor())
    }


    @OptIn(UnstableApi::class)
    private fun playFromRaw() {

        val rawRes = R.raw.alone
        val uri =
            Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .path(rawRes.toString())
                .build()

        val artwork = R.mipmap.alone
        val artworkUri =
            Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .path(artwork.toString())
                .build()

        this.player = ExoPlayer.Builder(this).build()
        val mediaMetaData = MediaMetadata.Builder()
            .setTitle("Alone")
            .setArtist("Alex Productions")
            .setAlbumTitle("Album title test")
            .setAlbumArtist("Album artist test")
            .setArtworkUri(artworkUri)
            .build()
        val mediaItem = MediaItem.Builder().setUri(uri).setMediaMetadata(mediaMetaData).build()

        this.player?.apply {
            addListener(object : Player.Listener {
                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    super.onMediaMetadataChanged(mediaMetadata)
                    Log.i("TAG23", "Meta data changed")
                    Log.i("TAG23", "Title: ${mediaMetadata.title}")
                    Log.i("TAG23", "Media type: ${mediaMetadata.mediaType}")
                    Log.i("TAG23", "Album artist: ${mediaMetadata.albumArtist}")
                    Log.i("TAG23", "Album title: ${mediaMetadata.albumTitle}")
                    Log.i("TAG23", "Artwork uri: ${mediaMetadata.artworkUri}")
                    Log.i("TAG23", "Meta data changed")
                }

                override fun onMetadata(metadata: Metadata) {
                    super.onMetadata(metadata)
                    Log.i("TAG23", "Meta data ${metadata}")
                }
            })

            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        this.player?.apply {
            release()
        }
        this.player = null
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DroidDevTipsTheme {
        Greeting("Android")
    }
}