package com.torontocodingcollective.sensors.encoder;

import edu.wpi.first.wpilibj.Encoder;

public class TDioEncoder extends TEncoder {

	Encoder encoder;
	
	/**
	 * Encoder constructor. Construct a Encoder given two dio channels a and b.
	 * <p>
	 * The encoder is not inverted.
	 * @param dioChannelA The a channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param dioChannelB The b channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 *
	 */
	public TDioEncoder(int dioChannelA, int dioChannelB) {
		this(dioChannelA, dioChannelB, false);
	}

	/**
	 * Encoder constructor. Construct a Encoder given two dio channels a and b.
	 * <p>
	 * The encoder counts are inverted (negated) based on the isInverted parameter.
	 * @param dioChannelA The a channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param dioChannelB The b channel DIO channel. 0-9 are on-board, 10-25 are on the MXP port
	 * @param isInverted  Inversion orientation of this encoder {@code true} if inverted, {@code false}
	 * otherwise.
	 */
	public TDioEncoder(int dioChannelA, int dioChannelB, boolean isInverted) {
		super(isInverted);
		this.encoder = new Encoder(dioChannelA, dioChannelB);
	}
	
	@Override
	public int get() {
		return super.get(encoder.get());
	}

	@Override
	public double getRate() {
		return super.getRate(encoder.getRate());
	}

}
