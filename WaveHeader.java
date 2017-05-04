package com.liyuqi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * wav文件头信息辅助类
 * 说明：有关音频文件的专业详细信息，此处描述可能并不准确，仅做功能实现的参考，具体详细准确描述，请自行度娘或咨询专业人士。
 * 附：
 * 8KHz采样、16比特量化的线性PCM语音信号的WAVE文件头格式表（共44字节）
     偏移地址  字节数   数据类型    内容                                       文件头定义为
     00H        4		 char       "RIFF"                                     char riff_id[4]="RIFF"
     04H        4		 long       int 文件总长-8                             long int size0=文总长-8
     08H        8		 char       "WAVEfmt "                                 char wave_fmt[8]
     10H        4		 long/int   10 00 00 00H(PCM)sizeof(PCMWAVEFORMAT)     long int size1=0x10
     14H        2		 int        01 00H格式类别，1表示为PCM形式的声音数据   int fmttag=0x01
     16H        2		 int        通道数，单声道为1，双声道为2               int channel=1 或2
     18H        4		 long/int   采样率                                     long int samplespersec
     1CH        4		 long/int   每秒播放字节数                             long int bytepersec
     20H        2		 int        采样一次占字节数                           int blockalign=声道数*量化数/8
     22H        2		 int        量化数                                     int bitpersamples=8或16
     24H        4		 char       "data"                                     char data_id="data"
     28H        4		 long/int   采样数据字节数                             long int size2=文长-44
     2CH        到文尾   char       采样数据
 * @author liyuqi
 * @date 2017年1月11日 下午6:09:19
 */
public class WaveHeader {
	   public final char fileID[] = {'R', 'I', 'F', 'F'};
	   public int fileLength;
	   public char wavTag[] = {'W', 'A', 'V', 'E'};;
	   public char FmtHdrID[] = {'f', 'm', 't', ' '};
	   public int FmtHdrLeth;
	   public short FormatTag;
	   public short Channels;
	   public int SamplesPerSec;
	   public int AvgBytesPerSec;
	   public short BlockAlign;
	   public short BitsPerSample;
	   public char DataHdrID[] = {'d','a','t','a'};
	   public int DataHdrLeth;

	public byte[] getHeader() throws IOException {
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   WriteChar(bos, fileID);
	   WriteInt(bos, fileLength);
	   WriteChar(bos, wavTag);
	   WriteChar(bos, FmtHdrID);
	   WriteInt(bos,FmtHdrLeth);
	   WriteShort(bos,FormatTag);
	   WriteShort(bos,Channels);
	   WriteInt(bos,SamplesPerSec);
	   WriteInt(bos,AvgBytesPerSec);
	   WriteShort(bos,BlockAlign);
	   WriteShort(bos,BitsPerSample);
	   WriteChar(bos,DataHdrID);
	   WriteInt(bos,DataHdrLeth);
	   bos.flush();
	   byte[] r = bos.toByteArray();
	   bos.close();
	   return r;
	}

	private void WriteShort(ByteArrayOutputStream bos, int s) throws IOException {
	   byte[] mybyte = new byte[2];
	   mybyte[1] =(byte)( (s << 16) >> 24 );
	   mybyte[0] =(byte)( (s << 24) >> 24 );
	   bos.write(mybyte);
	}


	private void WriteInt(ByteArrayOutputStream bos, int n) throws IOException {
	   byte[] buf = new byte[4];
	   buf[3] =(byte)( n >> 24 );
	   buf[2] =(byte)( (n << 8) >> 24 );
	   buf[1] =(byte)( (n << 16) >> 24 );
	   buf[0] =(byte)( (n << 24) >> 24 );
	   bos.write(buf);
	}

	private void WriteChar(ByteArrayOutputStream bos, char[] id) {
	   for (int i=0; i<id.length; i++) {
	      char c = id[i];
	      bos.write(c);
	   }
	}
	}