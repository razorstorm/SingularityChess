package com.petrifiednightmares.singularityChess.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;

import com.petrifiednightmares.singularityChess.R;
import com.petrifiednightmares.singularityChess.utilities.SingularBitmapFactory;

public class SUI
{

	public static int WIDTH, HEIGHT, MIN_DIMENSION, UNIT, PADDING, PIECE_SIZE, TOP_PADDING,
			CIRCLE_RADIUS_DIFFERENCE, TOP_BAR_BOTTOM, BORDER_WIDTH, HEIGHT_CENTER, BOTTOM;

	public static Paint darkPaint, lightPaint, highlightPaint, attackPaint, piecePaint, labelPaint,
			flashPaint, kingThreatenPaint, turnNamePaint, topBarPaint, topBarTexturePaint,
			turnNameWhitePaint, turnNameBlackPaint, borderPaint, borderShadowPaint,
			boardLightingPaint;
	private static Bitmap _darkTexture, _lightTexture, _topBarTexture, _borderTexture;

	public static MediaPlayer pieceSound;

	public static void setup(int width, int height, Resources r, Context c)
	{
		setupMeasurements(width, height);

		setupSimplePaints();

		setupTexturedPaints(r);

		setupAudio(c);
	}

	private static void setupMeasurements(int width, int height)
	{
		WIDTH = width;
		HEIGHT = height;
		MIN_DIMENSION = Math.min(WIDTH, HEIGHT);
		UNIT = (int) (MIN_DIMENSION / 100.0);
		PADDING = 5 * UNIT;
		PIECE_SIZE = 10 * UNIT;
		TOP_PADDING = 70; // Derived from: 10 * UNIT;
		TOP_BAR_BOTTOM = 56; // Derived from: 8 * UNIT;
		CIRCLE_RADIUS_DIFFERENCE = 11 * UNIT; // 12
		BORDER_WIDTH = (WIDTH / 2 - PADDING) - 4 * CIRCLE_RADIUS_DIFFERENCE;
		HEIGHT_CENTER = TOP_PADDING + 6 * CIRCLE_RADIUS_DIFFERENCE + BORDER_WIDTH;
		BOTTOM = HEIGHT_CENTER + 6 * CIRCLE_RADIUS_DIFFERENCE + BORDER_WIDTH + 60 + 10; // 60
																						// from
																						// shadow,
																						// 10
																						// from
																						// padding
	}

	private static void setupSimplePaints()
	{
		borderShadowPaint = new Paint();
		borderShadowPaint.setColor(Color.BLACK);
		borderShadowPaint.setShadowLayer(40, 20, 20, Color.argb(200, 0, 0, 0));

		highlightPaint = new Paint();
		highlightPaint.setColor(Color.rgb(36, 109, 218));
		highlightPaint.setAlpha(200);
		highlightPaint.setAntiAlias(true);

		flashPaint = new Paint();
		flashPaint.setColor(Color.rgb(0, 255, 0));
		flashPaint.setAlpha(200);
		flashPaint.setAntiAlias(true);

		attackPaint = new Paint();
		attackPaint.setColor(Color.rgb(205, 52, 52));
		attackPaint.setAlpha(200);
		attackPaint.setAntiAlias(true);

		kingThreatenPaint = new Paint();
		kingThreatenPaint.setColor(Color.rgb(255, 127, 0));
		kingThreatenPaint.setAntiAlias(true);

		piecePaint = new Paint();
		piecePaint.setAntiAlias(true);

		labelPaint = new Paint();
		labelPaint.setColor(Color.RED);

		turnNamePaint = new Paint();
		turnNamePaint.setColor(Color.WHITE);
		turnNamePaint.setShadowLayer(2, 0, 0, Color.BLACK);
		turnNamePaint.setTextSize(25);
		turnNamePaint.setAntiAlias(true);

		turnNameBlackPaint = new Paint();
		turnNameBlackPaint.setColor(Color.BLACK);
		turnNameBlackPaint.setAntiAlias(true);

		turnNameWhitePaint = new Paint();
		turnNameWhitePaint.setColor(Color.WHITE);
		turnNameWhitePaint.setAntiAlias(true);
	}

	private static void setupTexturedPaints(Resources r)
	{
		_darkTexture = BitmapFactory.decodeResource(r, R.drawable.wood_pattern);
		BitmapShader darkShader = new BitmapShader(_darkTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		darkPaint = new Paint();
		darkPaint.setShader(darkShader);
		darkPaint.setAntiAlias(true);
		darkPaint.setFilterBitmap(true);

		_lightTexture = BitmapFactory.decodeResource(r, R.drawable.retina_wood_1);
		BitmapShader lightShader = new BitmapShader(_lightTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		_topBarTexture = SingularBitmapFactory.buildBitmap(r, R.drawable.wild_oliva);
		BitmapShader topBarShader = new BitmapShader(_topBarTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);

		topBarTexturePaint = new Paint();
		topBarTexturePaint.setShader(topBarShader);
		topBarTexturePaint.setAntiAlias(true);
		topBarTexturePaint.setFilterBitmap(true);

		lightPaint = new Paint();
		lightPaint.setShader(lightShader);
		lightPaint.setAntiAlias(true);
		lightPaint.setFilterBitmap(true);

		_borderTexture = BitmapFactory.decodeResource(r, R.drawable.leather);
		BitmapShader borderShader = new BitmapShader(_borderTexture, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);
		borderPaint = new Paint();
		borderPaint.setShader(borderShader);
		borderPaint.setAntiAlias(true);
		borderPaint.setFilterBitmap(true);

		topBarPaint = new Paint();
		topBarPaint.setAntiAlias(true);
		topBarPaint.setShader(new LinearGradient(0, 0, 0, TOP_BAR_BOTTOM, Color.WHITE, Color.rgb(
				50, 50, 50), Shader.TileMode.MIRROR));
		topBarPaint.setAlpha(150);

		boardLightingPaint = new Paint();
		boardLightingPaint.setAntiAlias(true);
		boardLightingPaint.setShader(new RadialGradient(WIDTH / 2f+6*UNIT, (float) HEIGHT_CENTER-4*UNIT,
				5f * CIRCLE_RADIUS_DIFFERENCE, new int[] { Color.rgb(255, 255, 200), Color.argb(255,20,20,0) }, new float[] {
						0.3f, 0.8f }, Shader.TileMode.MIRROR));
		boardLightingPaint.setAlpha(80);
	}

	private static void setupAudio(Context c)
	{
		pieceSound = MediaPlayer.create(c, R.raw.piece);
	}
}
