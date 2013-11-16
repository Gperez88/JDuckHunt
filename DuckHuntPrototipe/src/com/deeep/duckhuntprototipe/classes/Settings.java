package com.deeep.duckhuntprototipe.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.gdx.Gdx;

public class Settings {
	public static boolean soundEnabled = true;
	public final static String file = ".duckhuntprototipe";

	public static void load() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(Gdx.files.external(
					file).read()));
			soundEnabled = Boolean.parseBoolean(in.readLine());
		} catch (Throwable e) {
			// :( It's ok we have defaults
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save() {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(
					file).write(false)));
			out.write(Boolean.toString(soundEnabled));
		} catch (Throwable e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}
}
