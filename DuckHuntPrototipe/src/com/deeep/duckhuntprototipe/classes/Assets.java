package com.deeep.duckhuntprototipe.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {
	public static TextureAtlas background;
	public static TextureRegion backgroundRegion;

	public static TextureAtlas items;
	public static TextureRegion dog;
	public static TextureRegion pause;
	public static TextureRegion ready;

	public static Animation duckFly;

	public static BitmapFont font;

	public static Music music;

	public static Sound hitSound;
	public static Sound missSound;

	public static void load() {
		background = new TextureAtlas(
				Gdx.files.internal("data/background.pack"),
				Gdx.files.internal("data"));
		backgroundRegion = background
				.findRegion("Duck_Hunt_Wallpaper_by_jmorrison02766");

		items = new TextureAtlas(Gdx.files.internal("data/items.pack"),
				Gdx.files.internal("data"));
		dog = items.findRegion("dogSmelling1");
		duckFly = new Animation(0.2f,
				((TextureRegion) items.findRegion("duckFlyRight1")),
				((TextureRegion) items.findRegion("duckFlyRight2")));

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("data/font.ttf"));
		font = generator.generateFont(62);
		generator.dispose();

		// music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		// music.setLooping(true);
		// music.setVolume(0.5f);
		// if (Settings.soundEnabled)
		// music.play();
		//
		// hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
		// missSound = Gdx.audio.newSound(Gdx.files.internal("data/miss.wav"));
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}
}
