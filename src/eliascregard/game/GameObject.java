package eliascregard.game;

import eliascregard.util.Vector2;

import java.awt.image.BufferedImage;

public record GameObject(double depth, BufferedImage wallColumn, Vector2 position) {}
